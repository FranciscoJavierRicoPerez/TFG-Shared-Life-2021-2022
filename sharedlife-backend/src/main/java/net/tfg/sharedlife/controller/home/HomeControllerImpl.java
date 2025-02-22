package net.tfg.sharedlife.controller.home;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.dto.NewUserDto;
import net.tfg.sharedlife.dto.UserDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.exception.HomeException;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.service.home.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class HomeControllerImpl.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {
	
	private final static Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

	@Autowired
	private HomeService homeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	@PostMapping
	public ResponseEntity<Home> createHome(HomeDTO homeDTO) {
		logger.info("Starting creating house process...");
		Home home = null;
		HttpStatus status = HttpStatus.OK;
		try {
			home = homeService.createHome(homeDTO);
		}catch(HomeException e) {
			logger.error("Error creating home");
			status = HttpStatus.BAD_REQUEST;	
		}
		return new ResponseEntity<>(home, status);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Override
	@GetMapping("/byUsername")
	public ResponseEntity<List<HomeDTO>> getHomesByUser(@RequestParam("username") String username){
		logger.info("Finding the houses for user with username: {}", username);
		List<HomeDTO> homes = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;
		try{
			homes = homeService.getHomesByUser(username);
		} catch(DataIncorrectException e){
			logger.error("Error getting the homes of the user: {}", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(homes, status);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Override
	@GetMapping("/id/{id}")
	public ResponseEntity<HomeDTO> getHomeById(@PathVariable("id") Long id){
		logger.info("Searching the house with id: {}", id);
		HomeDTO home = null;
		HttpStatus status = HttpStatus.OK;
		try{
			home = homeService.getHomeDtoById(id);
		}catch(DataIncorrectException e){
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(home, status);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	@PostMapping("/invitation")
	public ResponseEntity<?> createInvitationToHome(@RequestBody InvitationDTO invitation){
		logger.info("Sending a invitation to user with username: {}", invitation.getUsername());
		HttpStatus status = HttpStatus.OK;
		try {
			homeService.createInvitation(invitation);
		}catch(DataIncorrectException e) {
			if(e.getMessage().equals(ErrorMessages.USER_ALREADY_HAVE_HOME_ERR)) {
				status = HttpStatus.BAD_REQUEST;
			}
			if(e.getMessage().endsWith(ErrorMessages.USER_ALREADY_INVITED)) {
				status = HttpStatus.valueOf(401);
			}
			if(e.getMessage().equals(ErrorMessages.USER_NOT_FOUND)){
				status = HttpStatus.valueOf(503);
			}
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasRole('USER')")
	@Override
	@PostMapping("/invitation/accept")
	public ResponseEntity<?> acceptInvitationToHome(@RequestBody InvitationDTO invitation) {
		// ESTE METODO TIENE QUE AÑADIR AL USUARIO A LA TAMBLA HOME_USER SI EL HOME_CODE PASADO ES EL MISMO QUE ESTA EN LA TABLA
		// BORRAR DE LA TABLA DE INVITACIONES TODAS LAS INVITACIONES QUE TENGA EL USUARIO
		logger.info("Starting accept invitation process for user with username: {} to home with id: {}", invitation.getUsername(), invitation.getIdHome());
		try{
			homeService.acceptInvitation(invitation);
		} catch (TasksException e){
			logger.info("error");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@Override
	@GetMapping("/id/{id}/members")
	public ResponseEntity<List<UserDTO>> getMembers(@PathVariable("id") Long id){
		//// PUEDE SER QUE NO FUNCIONE YA QUE AL HACER EN EL FRONT EL window.localtion.reload NO SE SI SIE PIERDE EL VALOR DE ID HOME
		logger.info("Searching members of the house with id: {}", id);
		List<UserDTO> users = new ArrayList<>();
		users = homeService.getMembers(id);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@Override
	@DeleteMapping("/id/{id}/leave")
	public ResponseEntity<?> leaveHome(Long id, String username) {
		logger.info("User {} is leaving the home with id {}", username, id);
		homeService.leaveHome(id, username);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	@DeleteMapping("/id/{id}/delete")
	public ResponseEntity<?> deleteHome(Long id) {
		logger.info("Delete of the home with id: {}", id);
		homeService.deleteHome(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@GetMapping("/hasHome")
	public ResponseEntity<Boolean> hasHome(String username) {
		Boolean hasHomes = false;
		logger.info("Searching homes for user with username: {}", username);
		hasHomes = homeService.hasHome(username);
		return new ResponseEntity<>(hasHomes, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostMapping("/id/{id}/weeklyTaskManagement")
	@Override
	public ResponseEntity<?> weeklyTaskManagement(@PathVariable("id") Long id, @RequestBody List<Long> ids) {
		HttpStatus status = HttpStatus.OK;
		homeService.weeklyTaskReasignationManagement(id, ids);
		return new ResponseEntity<>(status);
	}
}
