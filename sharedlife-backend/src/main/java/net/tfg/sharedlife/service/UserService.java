package net.tfg.sharedlife.service;

import java.util.List;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;

public interface UserService {

	User createUser(User user) throws DataIncorrectException;
	
	List<User> getAllUsers();
	
	User findUserById(Long id);
	
}
