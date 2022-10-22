package net.tfg.sharedlife.service.home;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.tfg.sharedlife.dto.*;
import net.tfg.sharedlife.model.*;
import net.tfg.sharedlife.service.spent.SpentService;
import net.tfg.sharedlife.service.task.TaskService;

import org.apache.logging.log4j.util.Strings;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.mapper.HomeMapper;
import net.tfg.sharedlife.repository.HomeRepository;
import net.tfg.sharedlife.repository.InvitationRepository;
import net.tfg.sharedlife.security.jwt.JwtProvider;
import net.tfg.sharedlife.service.user.UserService;

/**
 * The Class HomeServiceImpl.
 */
@Service
public class HomeServiceImpl implements HomeService {

	/** The home repository. */
	@Autowired
	private HomeRepository homeRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	@Lazy
	private SpentService spentService;

	@Autowired
	private TaskService taskService;

	private HomeMapper homeMapper = Mappers.getMapper(HomeMapper.class);

	@Autowired
	JwtProvider jwtProvider;
	
	@Value("${jwt.secret}")
	private String secret;
	
	private final static Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

	@Override
	public Home createHome(HomeDTO homeDTO) throws DataIncorrectException {
		if(null == homeDTO) {
			throw new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR);
		}
		String username = jwtProvider.getUsernameFromToken(homeDTO.getToken());
		User user = userService.getByUsername(username).get();
		Home newHome = homeMapper.homeDTOtoHome(homeDTO);
		Set<User> users = new HashSet<>();
		users.add(user);
		newHome.setUsers(users);
		newHome.setActualMemberCount(1); // Por defecto 1 que sera el administrador
		homeRepository.save(newHome);
		return newHome;
	}
	
	@Override
	public List<HomeDTO> getHomesByUser(String username) throws DataIncorrectException{
		logger.info("Getting the homes of the user: {}", username);
		if(Strings.isBlank(username)){
			throw new DataIncorrectException(ErrorMessages.USERNAME_NOT_CORRECT);
		}
		List<HomeDTO> homes = new ArrayList<>();
		User user = userService.getByUsername(username).get();
		List<Home> aux = homeRepository.findAll();
		for(Home home : aux) {
			for(User u : home.getUsers()) {
				if(u.equals(user)) {
					homes.add(homeMapper.homeToHomeDTO(home));
				}
			}
		}
		return homes;
	}
	
	@Override
	public HomeDTO getHomeById(Long id) throws DataIncorrectException{
		Home home = homeRepository.findById(id).orElse(null);
		if(null == home){
			throw new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR);
		}
		HomeDTO homedto = homeMapper.homeToHomeDTO(home);
		if(checkHomeIsCompleted(id)) {
			homedto.setCompleted(true);
		}
		return homedto;
	}

	@Override
	public void createInvitation(InvitationDTO invitation) throws DataIncorrectException{
		
		// Comprobamos si el usuario ya existe en alguna vivienda
		if(checkUserHaveHome(invitation.getUsername())) {
			throw new DataIncorrectException(ErrorMessages.USER_ALREADY_HAVE_HOME_ERR);
		}
		if(checkHomeIsCompleted(Long.parseLong(invitation.getIdHome()))) {
			throw new DataIncorrectException(ErrorMessages.HOME_IS_COMPLETED);
		}
		if(checkUserNotHaveInvitation(invitation.getUsername(), invitation.getIdHome())) {
			throw new DataIncorrectException(ErrorMessages.USER_ALREADY_INVITED);
		}
		if(checkUserNotExists(invitation.getUsername())){
			throw new DataIncorrectException(ErrorMessages.USER_NOT_FOUND);
		}
		Invitation i = new Invitation();
		i.setIdHome(invitation.getIdHome());
		i.setUsername(invitation.getUsername());
		// EL CODIGO DE LA VIVIENDA ES EL NOMBRE DEL USUARIO + SECRET + LA DIRECCION DE LA VIVIENDA
		String toEncode = invitation.getUsername() + secret + invitation.getAddress();
		Encoder encoder = Base64.getEncoder();
		i.setHomeCode(encoder.encodeToString(toEncode.getBytes()));
		invitationRepository.save(i);
	}
	
	@Override
	public void acceptInvitation(InvitationDTO invitationDTO){
		logger.info("Starting the process of join to home");
		User user = userService.getByUsername(invitationDTO.getUsername()).get();
		Home home = homeRepository.findById(Long.parseLong(invitationDTO.getIdHome())).get();
		Set<User> users = home.getUsers();
		users.add(user);
		home.setUsers(users);
		home.setActualMemberCount(home.getActualMemberCount()+1); // Añadimos a un nuevo inquilino
		
		List<Invitation> invitations = invitationRepository.findAll();
		Invitation invitation = new Invitation();
		for(Invitation i : invitations) {
			if(i.getHomeCode().equals(invitationDTO.getHomeCode())) {
				invitation = i;
			}
		}
		List<Invitation> invitationsByUsername = invitationRepository.findByUsername(invitation.getUsername());
		invitationRepository.deleteAll(invitationsByUsername);
		logger.info("User with username: {} added to house with id: {}", user.getUsername(), home.getId());
	}

	@Override
	public List<NewUserDto> getMembers(Long idHome) {
		List<NewUserDto> usersDTO = new ArrayList<>();
		Home home = homeRepository.findById(idHome).get();
		Set<User> users = home.getUsers();
		for(User u : users) {
			NewUserDto userdto = new NewUserDto();
			userdto.setFirstName(u.getFirstName());
			userdto.setLastName(u.getLastName());
			userdto.setEmail(u.getEmail());
			userdto.setUsername(u.getUsername());
			//userdto.setPassword(u.getPassword());
			Set<String> roles = new HashSet<>();
			for(Role r : u.getRoles()) {
				String role = "";
				if(r.getRoleName().equals(RoleEnum.ROLE_ADMIN)) {
					role = "ROLE_ADMIN";
				}
				else {
					role = "ROLE_USER";
				}
				roles.add(role);
			}
			userdto.setRoles(roles);
			usersDTO.add(userdto);
		}
		
		logger.info("Members of the home with id: {} obtanied succesfully", idHome);
		return usersDTO;
	}
	
	@Override
	public void leaveHome(Long idHome, String username) {
		User user = userService.getByUsername(username).get();
		Home home = homeRepository.findById(idHome).get();
		logger.info("Deleting the tasks of the user {} for home {}", username, idHome);
		try{
			for(TaskDTO t : taskService.getTasksByUsernameAndHomeId(username, idHome)){
				taskService.deleteTask(t.getId());
			}
			logger.info("Deleting the spents of the user {} for home {}", username, idHome);
			for(SpentDTO s : spentService.getSpentsByUsernameAndHomeId(username, idHome)){
				spentService.deleteSpent(s.getId());
			}
			home.getUsers().remove(user);
			homeRepository.save(home);
		}catch(TasksException e){
			logger.info("Err deleting task");
		}
	}
	
	@Override
	public void deleteHome(Long id) {
		logger.info("Deleting the home with id: {}", id);
		Home home = homeRepository.findById(id).get();
		try{
			for(Spent s : home.getSpents()){
				spentService.deleteSpent(s.getId());
			}
			for(Task t : home.getTasks()){
				taskService.deleteTask(t.getId());
			}
			homeRepository.delete(home);
		}catch(TasksException e){
			logger.info("Error deleting home");
		}
	}

	
	private boolean checkUserHaveHome(String username) {
		boolean have = false;
		List<Home> homes = homeRepository.findAll();
		for(Home home : homes) {
			for(User user: home.getUsers()) {
				if(user.getUsername().equals(username)) {
					have = true;
				}
			}
		}
		return have;
	}
	
	private boolean checkHomeIsCompleted(Long id) {
		boolean completed = false;
		List<Home> homes = homeRepository.findAll();
		for(Home home : homes) {
			if(home.getId().equals(id)) {
				int rooms = Integer.parseInt(home.getRooms());
				if((this.getMembers(id).size() - 1) == rooms) {
					completed = true;
				}
			}
		}
		return completed;
	}

	@Override
	public Boolean hasHome(String username) {
		Boolean hasHome = false;
		List<Home> homes = homeRepository.findAll();
		for(Home h : homes) {
			for(User u : h.getUsers()) {
				if(username.equals(u.getUsername())) {
					hasHome = true;
				}
			}
		}
		return hasHome;
	}
	
	private boolean checkUserNotHaveInvitation(String username, String idHome) {
		boolean hasInvitation = false;
		List<Invitation> invitations = invitationRepository.findAll();
		for(Invitation i : invitations) {
			if(i.getUsername().equals(username) && i.getIdHome().equals(idHome)) {
				hasInvitation = true;
			}
		}
		return hasInvitation;
	}

	private boolean checkUserNotExists(String username){
		boolean notExists = false;
		User u = userService.getByUsername(username).orElse(null);
		if(u  == null){
			notExists = true;
		}
		return notExists;
	}

}
