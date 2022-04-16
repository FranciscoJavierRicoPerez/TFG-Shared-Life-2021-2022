package net.tfg.sharedlife.controller.home;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
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
	public ResponseEntity<?> createHome(HomeDTO home) {
		logger.info("Starting creating house process...");
		try {
			homeService.createHome(home);
		}catch(DataIncorrectException e) {
			logger.error("Error creating home");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Override
	@GetMapping("/byUsername")
	public ResponseEntity<List<HomeDTO>> getHomesByUser(@RequestParam("username") String username){
		logger.info("Finding the houses for user with username: {}", username);
		List<HomeDTO> homes = new ArrayList<>();
		homes = homeService.getHomesByUser(username);
		return new ResponseEntity<>(homes, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Override
	@GetMapping("/id/{id}")
	public ResponseEntity<HomeDTO> getHomeById(@PathVariable("id") Long id){
		logger.info("Searching the house with id: {}", id);
		HomeDTO home = new HomeDTO();
		home = homeService.getHomeById(id);
		return new ResponseEntity<>(home, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	@PostMapping("/invitation")
	public ResponseEntity<?> createInvitationToHome(@RequestBody InvitationDTO invitation){
		logger.info("Sending a invitation to user with username: {}", invitation.getUsername());
		homeService.createInvitation(invitation);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
