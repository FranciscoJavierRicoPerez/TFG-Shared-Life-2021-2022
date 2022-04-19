package net.tfg.sharedlife.controller.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createTask(@RequestBody TaskDTO task){
		Log.info("Creatring a new task for user with username: {}", task.getUser());
		if(null != task) {
			taskService.createTask(task);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
