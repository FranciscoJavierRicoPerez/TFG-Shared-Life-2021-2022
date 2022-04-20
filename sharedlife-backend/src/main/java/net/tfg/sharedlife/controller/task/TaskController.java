package net.tfg.sharedlife.controller.task;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskController {
	
	@PostMapping("/create")
	ResponseEntity<?> createTask(@RequestBody TaskDTO task);
	
	@GetMapping("/byUsername")
	ResponseEntity<List<TaskDTO>> getTasksByUsername(@RequestParam("username") String username);
	
	@GetMapping("/byHomeId/{id}")
	ResponseEntity<List<TaskDTO>> getTasksByHomeIdAndUsername(@PathVariable("id") Long id, @RequestParam("username") String username);
	
	@PutMapping("/{id}/finished")
	ResponseEntity<?> updateFinishedStatus(@PathVariable("id") Long id, @RequestBody boolean finished);

}
