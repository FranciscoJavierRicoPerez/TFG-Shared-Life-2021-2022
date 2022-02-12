package net.tfg.sharedlife.service.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.repository.HomeRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeServiceImpl.
 */
@Service
public class HomeServiceImpl implements HomeService {

	/** The home repository. */
	@Autowired
	private HomeRepository homeRepository;

	/**
	 * Creates the home.
	 *
	 * @param home the home
	 * @return the home
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public Home createHome(Home home) throws DataIncorrectException {
		if (home.getAddress().isBlank() || home.getCity().isBlank() || home.getCountry().isBlank()
				|| home.getNumber().isBlank() || home.getRooms().isBlank() || home.getFloor().isBlank()) {
			throw new DataIncorrectException(ErrorMessages.HOME_INFORMATION_ERR);
		}
		return homeRepository.save(home);
	}

}
