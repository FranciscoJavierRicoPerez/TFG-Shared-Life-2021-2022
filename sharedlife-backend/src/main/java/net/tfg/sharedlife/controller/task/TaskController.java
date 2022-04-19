package net.tfg.sharedlife.controller.task;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskController {
	
	@PostMapping("/create")
	ResponseEntity<?> createTask(@RequestBody TaskDTO task);
	
	@GetMapping("/byUsername")
	ResponseEntity<List<TaskDTO>> getTasksByUsername(@RequestParam("username") String username);

}
