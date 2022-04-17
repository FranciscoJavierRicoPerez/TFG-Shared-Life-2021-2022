package net.tfg.sharedlife.controller.user;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.AuthUserDto;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.service.user.UserService;

/**
 * The Class UserControllerImpl.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController{
	
	private final static Logger Log = LoggerFactory.getLogger(UserControllerImpl.class);
	
	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	@GetMapping("/")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	/**
	 * Gets the user by id.
	 *
	 * @param idUser the id user
	 * @return the user by id
	 */
	@Override
	@GetMapping("/{idUser}")
	public ResponseEntity<AuthUserDto> getUserById(@PathVariable("idUser") Long idUser) {
		Log.info("Searching user with id: {}", idUser);
		var user = new User();
		try {
			user = userService.findUserById(idUser);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return new ResponseEntity<>(new AuthUserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername()),
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Override
	@GetMapping("/username")
	public ResponseEntity<AuthUserDto> getAuthUserByUserName(@RequestParam("username") String username) {
		Log.info("Searching user with username: {}", username);
		User user = userService.getByUsername(username)
				.orElseThrow(() -> new DataIncorrectException(ErrorMessages.USER_NOT_FOUND));
		return new ResponseEntity<>(
				new AuthUserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername()),
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@Override
	@GetMapping("/invitation")
	public ResponseEntity<List<InvitationDTO>> getInvitationByUsername(@RequestParam("username") String username){
		Log.info("Searching invitations for user with username: {}", username);
		List<InvitationDTO> invitations = new ArrayList<>();
		invitations = userService.getInvitationsByUsername(username);
		return new ResponseEntity<>(invitations, HttpStatus.OK);
	}
	
	
	
	
}
