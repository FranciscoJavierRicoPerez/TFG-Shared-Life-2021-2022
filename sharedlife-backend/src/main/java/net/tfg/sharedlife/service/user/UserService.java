package net.tfg.sharedlife.service.user;

import java.util.List;
import java.util.Optional;

import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws DataIncorrectException the data incorrect exception
	 */
	User createUser(User user) throws DataIncorrectException;
	
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();
	
	/**
	 * Find user by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	User findUserById(Long id);
	
	Optional<User> getByUsername(String userName);
	
	boolean existsByUsername(String userName);
	
	boolean existsByEmail(String email);
	
}
