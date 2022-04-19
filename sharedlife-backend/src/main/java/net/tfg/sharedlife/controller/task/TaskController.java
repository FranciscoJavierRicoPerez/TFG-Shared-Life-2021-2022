package net.tfg.sharedlife.controller.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskController {
	
	@PostMapping("/create")
	ResponseEntity<?> createTask(@RequestBody TaskDTO task);

}
