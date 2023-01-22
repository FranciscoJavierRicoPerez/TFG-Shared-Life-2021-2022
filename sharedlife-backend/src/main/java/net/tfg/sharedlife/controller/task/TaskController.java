package net.tfg.sharedlife.controller.task;

import java.util.List;

import net.tfg.sharedlife.dto.ConfirmedTaskDTO;
import net.tfg.sharedlife.dto.TaskStatusDTO;
import net.tfg.sharedlife.dto.TaskTrakingStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.model.Task;

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

	@GetMapping("/home/{id}/weeklyTasks")
	ResponseEntity<List<Task>> getWeeklyTasksByHomeId(@PathVariable("id") Long id);

	@PostMapping("/modifyTaskProgress")
	ResponseEntity<?> modifyTaskProgress(@RequestBody ConfirmedTaskDTO confirmedTaskDTO);

	@GetMapping("/checkTaskTraking")
	ResponseEntity<TaskTrakingStatusDTO> checkTaskTraking(@RequestParam("username") String username);

	@GetMapping("/checkTaskTrakingOfMyTasks")
	ResponseEntity<TaskTrakingStatusDTO> checkTaskTrakingOfMyTasks(@RequestParam("username") String username);

	@GetMapping("/checkAllTaskAreConfirmed")
	ResponseEntity<Boolean> checkAllTaskAreConfirmed(@RequestParam("ids") List<Long> ids);

	@PostMapping("/restartWeeklyTasks")
	ResponseEntity<?> restartWeeklyTasks(@RequestBody List<Long> ids);

}
