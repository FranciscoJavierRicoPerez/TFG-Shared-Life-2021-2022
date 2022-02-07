package net.tfg.sharedlife.controller;

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
import net.tfg.sharedlife.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class UserControllerImpl implements UserController{
	
	@Autowired
	private UserService userService;
	
	@Override
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		var newUser = new User();
		try {
			newUser = userService.createUser(user);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return newUser;
	}

	@Override
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{idUser}")
	public User getUserById(@PathVariable("idUser") Long idUser) {
		var user = new User();
		try {
			user = userService.findUserById(idUser);
		}catch(DataIncorrectException e) {
			System.err.println(e.getMessage());
		}
		return user;
	}
	
}
