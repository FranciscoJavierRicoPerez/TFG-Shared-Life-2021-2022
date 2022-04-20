package net.tfg.sharedlife.controller.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.service.task.TaskService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tasks")
public class TaskControllerImpl implements TaskController{
	
	private final static Logger Log = LoggerFactory.getLogger(TaskControllerImpl.class);
	
	@Autowired
	private TaskService taskService;
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PostMapping("/create")
	@Override
	public ResponseEntity<?> createTask(@RequestBody TaskDTO task){
		Log.info("Creatring a new task for user with username: {}", task.getUser());
		if(null != task) {
			taskService.createTask(task);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/byUsername")
	@Override
	public ResponseEntity<List<TaskDTO>> getTasksByUsername(@RequestParam("username") String username) {
		Log.info("Searching all tasks for user with username: {}", username);
		List<TaskDTO> tasks = new ArrayList<>();
		if(null != username) {
			tasks = taskService.getTasksByUsername(username);
		}
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/byHomeId/{id}")
	@Override
	public ResponseEntity<List<TaskDTO>> getTasksByHomeIdAndUsername(@PathVariable("id") Long id, @RequestParam("username") String username) {
		Log.info("Searching all task from house with id: {}", id);
		List<TaskDTO> tasks = new ArrayList<>();
		tasks = taskService.getTasksByHomeIdAndUsername(id, username);
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
}
