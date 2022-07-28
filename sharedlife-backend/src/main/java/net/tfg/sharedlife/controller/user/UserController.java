package net.tfg.sharedlife.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.AuthUserDto;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.model.User;

/**
 * The Interface UserController.
 */
public interface UserController {
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@GetMapping("/")
	List<User> getAllUsers();
	
	/**
	 * Gets the user by id.
	 *
	 * @param idUser the id user
	 * @return the user by id
	 */
	@GetMapping("/{idUser}")
	ResponseEntity<AuthUserDto> getUserById(@PathVariable("idUser") Long idUser);
	
	
	@GetMapping("/email")
	ResponseEntity<AuthUserDto> getAuthUserByUserName(@RequestParam("username") String username);
	
	@GetMapping("/invitation")
	ResponseEntity<List<InvitationDTO>> getInvitationByUsername(@RequestParam("username") String username);

	@DeleteMapping("/{id}/delete")
	ResponseEntity<?> deleteUser(@PathVariable("id") Long id);
}
