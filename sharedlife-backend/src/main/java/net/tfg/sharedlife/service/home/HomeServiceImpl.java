package net.tfg.sharedlife.service.home;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.model.Invitation;
import net.tfg.sharedlife.model.User;
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
	JwtProvider jwtProvider;
	
	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Creates the home.
	 *
	 * @param home the home
	 * @return the home
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public void createHome(HomeDTO home) throws DataIncorrectException {
		if(null == home) {
			throw new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR);
		}
		String username = jwtProvider.getUsernameFromToken(home.getToken());
		User user = userService.getByUsername(username).get();
		Home newHome = new Home(home.getAddress(), home.getNumber(), home.getFloor(), home.getCity(), home.getCountry(), home.getRooms());
		Set<User> users = new HashSet<>();
		users.add(user);
		newHome.setUsers(users);
		homeRepository.save(newHome);
	}
	
	@Override
	public List<HomeDTO> getHomesByUser(String username){
		List<HomeDTO> homes = new ArrayList<>();
		User user = userService.getByUsername(username).get();
		List<Home> aux = homeRepository.findAll();
		for(Home home : aux) {
			for(User u : home.getUsers()) {
				if(u.equals(user)) {
					HomeDTO homedto = new HomeDTO();
					homedto.setId(home.getId());
					homedto.setAddress(home.getAddress());
					homedto.setFloor(home.getFloor());
					homedto.setNumber(home.getNumber());
					homedto.setCity(home.getCity());
					homedto.setCountry(home.getCountry());
					homedto.setRooms(home.getRooms());
					homes.add(homedto);
				}
			}
		}
		return homes;
	}
	
	@Override
	public HomeDTO getHomeById(Long id) {
		Home home = homeRepository.findById(id).get();
		HomeDTO homedto = new HomeDTO();
		homedto.setId(home.getId());
		homedto.setAddress(home.getAddress());
		homedto.setFloor(home.getFloor());
		homedto.setNumber(home.getNumber());
		homedto.setCity(home.getCity());
		homedto.setCountry(home.getCountry());
		homedto.setRooms(home.getRooms());
		return homedto;
	}

	@Override
	public void createInvitation(InvitationDTO invitation) {
		Invitation i = new Invitation();
		i.setIdHome(invitation.getIdHome());
		i.setUsername(invitation.getUsername());
		String toEncode = invitation.getUsername() + secret;
		Encoder encoder = Base64.getEncoder();
		i.setHomeCode(encoder.encodeToString(toEncode.getBytes()));
		invitationRepository.save(i);
	}


}
