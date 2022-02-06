package net.tfg.sharedlife.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;

public interface UserService {

	User createUser(@RequestBody User user) throws DataIncorrectException;
	
	List<User> getAllUsers();
	
}
