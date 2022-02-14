package net.tfg.sharedlife.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.service.user.UserService;

/**
 * The Class UserControllerImpl.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class UserControllerImpl implements UserController{
	
	/** The user service. */
	@Autowired
	private UserService userService;
	
	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		System.out.println("Starting register user proccess...");
		var newUser = new User();
		try {
			newUser = userService.createUser(user);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return newUser;
	}

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	@GetMapping("/users")
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
	@GetMapping("/users/{idUser}")
	public User getUserById(@PathVariable("idUser") Long idUser) {
		System.out.println("Searching user with id: " + idUser);
		var user = new User();
		try {
			user = userService.findUserById(idUser);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return user;
	}
}
