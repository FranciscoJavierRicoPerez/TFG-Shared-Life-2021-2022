package net.tfg.sharedlife.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.service.home.HomeService;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeControllerImpl.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class HomeControllerImpl implements HomeController{

	/** The home service. */
	@Autowired
	private HomeService homeService;
	
	/**
	 * Creates the home.
	 *
	 * @param home the home
	 * @return the home
	 */
	@Override
	public Home createHome(Home home) {
		System.out.println("Starting creating house process...");
		Home newHome = null;
		try {
			newHome = homeService.createHome(home);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return newHome;
	}
	
}
