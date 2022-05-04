package net.tfg.sharedlife.controller.home;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.HomeDTO;
import net.tfg.sharedlife.dto.InvitationDTO;
import net.tfg.sharedlife.dto.NewUserDto;

/**
 * The Interface HomeController.
 */
public interface HomeController {
	
	@PostMapping("/")
	ResponseEntity<?> createHome(@RequestBody HomeDTO home);
	
	@GetMapping("/byUsername")
	ResponseEntity<List<HomeDTO>> getHomesByUser(@RequestParam("username") String username);
	
	@GetMapping("/id/{id}")
	ResponseEntity<HomeDTO> getHomeById(@PathVariable("id") Long id);
	
	@PostMapping("/invitation")
	ResponseEntity<?> createInvitationToHome(@RequestBody InvitationDTO invitation);
	
	@PostMapping("/invitation/accept")
	ResponseEntity<?> acceptInvitationToHome(@RequestBody InvitationDTO invitation);
	
	@GetMapping("/id/{id}/members")
	ResponseEntity<List<NewUserDto>> getMembers(@PathVariable("id") Long id);
	
	@DeleteMapping("/id/{id}/leave")
	ResponseEntity<?> leaveHome(@PathVariable("id") Long id, @RequestParam("username") String username);

	@DeleteMapping("/id/{id}/delete")
	ResponseEntity<?> deleteHome(@PathVariable("id") Long id);
}
