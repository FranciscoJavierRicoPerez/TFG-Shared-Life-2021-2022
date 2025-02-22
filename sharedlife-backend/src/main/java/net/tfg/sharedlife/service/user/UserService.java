package net.tfg.sharedlife.service.user;

import java.util.List;
import java.util.Optional;

import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	User createUser(User user) throws DataIncorrectException;
	
	List<User> getAllUsers();
	
	User findUserById(Long id);
	
	Optional<User> getByUsername(String userName);
	
	boolean existsByUsername(String userName);
	
	boolean existsByEmail(String email);
	
	List<InvitationDTO> getInvitationsByUsername(String username);

	void deleteUser(Long id);

	void saveUserInformation(User user);

	boolean updatePassword(String password, String email);

	String generateNewPassword(String email);

	boolean registerNewPassword(String email, String actualPassword, String newPassword);
}
