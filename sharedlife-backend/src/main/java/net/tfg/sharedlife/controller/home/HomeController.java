package net.tfg.sharedlife.controller.home;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;

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
	
	@GetMapping("/byUsername")
	ResponseEntity<List<HomeDTO>> getHomesByUser(@RequestParam("username") String username);
	
	@GetMapping("/id/{id}")
	ResponseEntity<HomeDTO> getHomeById(@PathVariable("id") Long id);
	
	@PostMapping("/invitation")
	ResponseEntity<?> createInvitationToHome(@RequestBody InvitationDTO invitation);
}
