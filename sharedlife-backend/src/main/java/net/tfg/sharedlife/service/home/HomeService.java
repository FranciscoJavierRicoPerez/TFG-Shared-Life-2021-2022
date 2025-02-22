package net.tfg.sharedlife.service.home;

import java.util.List;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.dto.NewUserDto;
import net.tfg.sharedlife.dto.UserDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.exception.HomeException;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.model.User;

/**
 * The Interface HomeService.
 */
public interface HomeService {
	
	Home createHome(HomeDTO home) throws HomeException;
	
	List<HomeDTO> getHomesByUser(String username);
	
	HomeDTO getHomeDtoById(Long id);
	
	void createInvitation(InvitationDTO invitation) throws DataIncorrectException;
	
	void acceptInvitation(InvitationDTO invitation) throws TasksException;
	
	List<UserDTO> getMembers(Long idHome);
	
	void leaveHome(Long idHome, String username);
	
	void deleteHome(Long id);
	
	Boolean hasHome(String username);

	Home getHomeById(Long id);

	List<User> getAllRentersByHomeId(Long id);

	void weeklyTaskManagement(Long id) throws TasksException;

	void weeklyTaskReasignationManagement(Long idHome, List<Long> idsWeeklyTask);

	boolean checkUserBelongToHome(Long homeId, Long userId);
	
}
