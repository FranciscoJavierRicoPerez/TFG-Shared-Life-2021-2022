package net.tfg.sharedlife.service;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User createUser(User user) throws DataIncorrectException{
		if(user.getFirstName().equals(Strings.EMPTY) 
				|| user.getLastName().equals(Strings.EMPTY) 
				|| user.getEmail().equals(Strings.EMPTY)) {
			throw new DataIncorrectException(ErrorMessages.USER_INFORMATION_ERR);
		}
		return userRepository.save(user);
	}


	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User findUserById(Long id) throws DataIncorrectException{
		if(null == id) {
			throw new DataIncorrectException(ErrorMessages.ID_NULL);
		}
		var user = userRepository.findById(id).orElse(null);
		if(null == user) {
			throw new DataIncorrectException(ErrorMessages.USER_NOT_FOUND);
		}
		return user;
	}

}
