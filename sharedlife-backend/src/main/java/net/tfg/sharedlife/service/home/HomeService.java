package net.tfg.sharedlife.service.home;

import java.util.List;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;

/**
 * The Interface HomeService.
 */
public interface HomeService {
	
	void createHome(HomeDTO home);
	
	List<HomeDTO> getHomesByUser(String username);
	
	HomeDTO getHomeById(Long id);
	
	void createInvitation(InvitationDTO invitation);
	
}
