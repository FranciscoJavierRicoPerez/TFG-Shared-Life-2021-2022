package net.tfg.sharedlife.service.home;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.HomeRepository;
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
	JwtProvider jwtProvider;
	
	@Autowired
	private UserService userService;

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

}
