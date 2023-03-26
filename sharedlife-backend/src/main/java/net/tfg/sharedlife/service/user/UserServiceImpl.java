package net.tfg.sharedlife.service.user;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Optional;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.service.home.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Invitation;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.InvitationRepository;
import net.tfg.sharedlife.repository.UserRepository;

import javax.transaction.Transactional;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService{

	private final static Logger Log = LoggerFactory.getLogger(UserServiceImpl.class);
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private HomeService homeService;
	
	@Autowired
	private InvitationRepository invitationRepository;

	@Value("${jwt.secret}")
	private String secret;


	
	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public User createUser(User user) throws DataIncorrectException { // PUEDE QUE NECESITE CAMBIARLO A UN VOID
		if(null == user) {
			throw new DataIncorrectException(ErrorMessages.USER_INFORMATION_ERR);
		}
		return userRepository.save(user);
	}


	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * Find user by id.
	 *
	 * @param id the id
	 * @return the user
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public User findUserById(Long id) throws DataIncorrectException{
		if(null == id) {
			throw new DataIncorrectException(ErrorMessages.ID_NULL);
		}
		//User user = userRepository.findById(id).orElse(null);
		List<User> users = userRepository.findAll();
		User user = new User();
		for(User u : users) {
			if(u.getId().equals(id)) {
				user = u;
			}
		}
		return user;
	}
	
	public Optional<User> getByUsername(String userName){
		return userRepository.getByUsername(userName);
	}
	
	public boolean existsByUsername(String userName) {
		return userRepository.existsByUsername(userName);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	@Override
	public List<InvitationDTO> getInvitationsByUsername(String username){
		List<InvitationDTO> invitationsDTO = new ArrayList<>();
		List<Invitation> invitations = invitationRepository.findByUsername(username);
		for(Invitation i : invitations) {
			InvitationDTO idto = new InvitationDTO();
			idto.setHomeCode(i.getHomeCode());
			idto.setIdHome(i.getIdHome());
			idto.setUsername(i.getUsername());
			invitationsDTO.add(idto);
		}
		return invitationsDTO;
	}

	@Override
	public void deleteUser(Long id){
		Log.info("Deleting the user with id: {}", id);
		User user = userRepository.getById(id);
		List<HomeDTO> homes = homeService.getHomesByUser(user.getUsername());
		// Comprobamos el tipo de usuario
		if(user.getRoles().size() > 1){ // ES ADMIN
			// Como es el administrador tenemos que borrar la vivienda
			for(HomeDTO home : homes){
				homeService.deleteHome(home.getId());
			}
		} else {
			// Como es el inquilino no tenemos que borrar la vivienda solo abandonarla
			for(HomeDTO home : homes){
				homeService.leaveHome(home.getId(), user.getUsername());
			}
		}
		userRepository.deleteById(id);
	}


	@Override
	public void saveUserInformation(User user) {
		Log.info("Saving infotmation of the user: " + user.getUsername());
		userRepository.save(user);
	}

	@Override
	@Transactional
	public boolean updatePassword(String password, String email) {
		boolean updated = false;
		User user = this.findUserByEmail(email);
		if(user != null) {
			user.setPassword(password);
			userRepository.save(user);
			updated = true;
		} else {
			updated = false;
		}
		return updated;
	}

	@Override
	public String generateNewPassword(String email) {
		String vars[] = email.split("@");
		String toEncode = vars[0] + secret;
		return toEncode;
	}

	private User findUserByEmail(String email){
		return userRepository.findByEmail(email);
	}

}
