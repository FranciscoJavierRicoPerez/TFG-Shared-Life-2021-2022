package net.tfg.sharedlife.controller.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.HomeDTO;

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
	ResponseEntity<?> createHome(@RequestBody HomeDTO home);
	
}
