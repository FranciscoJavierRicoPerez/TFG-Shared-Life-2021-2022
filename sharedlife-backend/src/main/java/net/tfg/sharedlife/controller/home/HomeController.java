package net.tfg.sharedlife.controller.home;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.model.Home;

// TODO: Auto-generated Javadoc
/**
 * The Interface HomeController.
 */
public interface HomeController {
	
	/**
	 * Creates the home.
	 *
	 * @param home the home
	 * @return the home
	 */
	@PostMapping("/")
	Home createHome(@RequestBody Home home);
	
}
