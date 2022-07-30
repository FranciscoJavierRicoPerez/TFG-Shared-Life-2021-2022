package net.tfg.sharedlife.controller.task;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskController {
	
	@PostMapping("/create")
	ResponseEntity<?> createTask(@RequestBody TaskDTO task);
	
	@GetMapping("/byHomeId/{id}/byUsername")
	ResponseEntity<List<TaskDTO>> getTasksByUsernameAndHomeId(@RequestParam("username") String username, @PathVariable("id") Long id);
	
	@GetMapping("/byHomeId/{id}")
	ResponseEntity<List<TaskDTO>> getTasksByHomeId(@PathVariable("id") Long id);
	
	@PutMapping("/{id}/finished")
	ResponseEntity<?> updateFinishedStatus(@PathVariable("id") Long id, @RequestBody boolean finished);

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteTask(@PathVariable("id") Long id);

}
