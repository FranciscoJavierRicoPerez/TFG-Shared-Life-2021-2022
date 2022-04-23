package net.tfg.sharedlife.controller.spent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.SpentDTO;

public interface SpentController {

	@PostMapping("/create")
	ResponseEntity<?> createSpent(@RequestBody SpentDTO spent, @RequestParam("admin") boolean admin);
	
}
