package net.tfg.sharedlife.controller.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import net.tfg.sharedlife.model.User;

/**
 * The Interface UserController.
 */
public interface UserController {

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@PostMapping("/users")
	User createUser(@RequestBody User user);

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@GetMapping("/users")
	List<User> getAllUsers();
	
	/**
	 * Gets the user by id.
	 *
	 * @param idUser the id user
	 * @return the user by id
	 */
	@GetMapping("/users/{idUser}")
	User getUserById(@PathVariable("idUser") Long idUser);
	
	
}
