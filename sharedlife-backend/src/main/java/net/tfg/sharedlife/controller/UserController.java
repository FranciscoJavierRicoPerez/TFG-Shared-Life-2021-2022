package net.tfg.sharedlife.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import net.tfg.sharedlife.model.User;

public interface UserController {

	@PostMapping("/users")
	User createUser(@RequestBody User user);

	@GetMapping("/users")
	List<User> getAllUsers();
	
	@GetMapping("/users/{idUser}")
	User getUserById(@PathVariable("idUser") Long idUser);
	
	
}
