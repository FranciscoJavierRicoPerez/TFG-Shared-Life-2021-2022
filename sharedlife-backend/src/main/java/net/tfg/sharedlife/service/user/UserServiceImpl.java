package net.tfg.sharedlife.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.model.Invitation;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.InvitationRepository;
import net.tfg.sharedlife.repository.UserRepository;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService{

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InvitationRepository invitationRepository;
	
	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public User createUser(User user) throws DataIncorrectException { // PUEDE QUE NECESITE CAMBIARLO A UN VOID
		if(null == user) {
			throw new DataIncorrectException(ErrorMessages.USER_INFORMATION_ERR);
		}
		return userRepository.save(user);
	}


	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * Find user by id.
	 *
	 * @param id the id
	 * @return the user
	 * @throws DataIncorrectException the data incorrect exception
	 */
	@Override
	public User findUserById(Long id) throws DataIncorrectException{
		if(null == id) {
			throw new DataIncorrectException(ErrorMessages.ID_NULL);
		}
		//User user = userRepository.findById(id).orElse(null);
		List<User> users = userRepository.findAll();
		User user = new User();
		for(User u : users) {
			if(u.getId().equals(id)) {
				user = u;
			}
		}
		return user;
	}
	
	public Optional<User> getByUsername(String userName){
		return userRepository.getByUsername(userName);
	}
	
	public boolean existsByUsername(String userName) {
		return userRepository.existsByUsername(userName);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	@Override
	public List<InvitationDTO> getInvitationsByUsername(String username){
		List<InvitationDTO> invitationsDTO = new ArrayList<>();
		List<Invitation> invitations = invitationRepository.findByUsername(username);
		for(Invitation i : invitations) {
			InvitationDTO idto = new InvitationDTO();
			idto.setHomeCode(i.getHomeCode());
			idto.setIdHome(i.getIdHome());
			idto.setUsername(i.getUsername());
			invitationsDTO.add(idto);
		}
		return invitationsDTO;
	}


}
