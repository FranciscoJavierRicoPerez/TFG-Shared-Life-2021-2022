package net.tfg.sharedlife.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.service.home.HomeService;

/**
 * The Class HomeControllerImpl.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController{
	
	private final static Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

	/** The home service. */
	@Autowired
	private HomeService homeService;
	
	/**
	 * Creates the home.
	 *
	 * @param home the home
	 * @return the home
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	@PostMapping
	public Home createHome(Home home) {
		logger.info("Starting creating house process...");
		Home newHome = null;
		try {
			newHome = homeService.createHome(home);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return newHome;
	}
	
}
