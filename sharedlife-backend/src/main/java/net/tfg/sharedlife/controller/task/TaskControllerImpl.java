package net.tfg.sharedlife.controller.task;

import java.util.ArrayList;
import java.util.List;

import net.tfg.sharedlife.dto.ConfirmedTaskDTO;
import net.tfg.sharedlife.dto.TaskTrakingDTO;
import net.tfg.sharedlife.dto.TaskTrakingStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.model.Task;
import net.tfg.sharedlife.service.task.TaskService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tasks")
public class TaskControllerImpl implements TaskController {
	
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
	@GetMapping("/byHomeId/{id}/byUsername")
	@Override
	public ResponseEntity<List<TaskDTO>> getTasksByUsernameAndHomeId(@RequestParam("username") String username, @PathVariable("id") Long id) {
		Log.info("Searching all tasks for user with username: {}", username);
		List<TaskDTO> tasks = new ArrayList<>();
		if(null != username) {
			tasks = taskService.getTasksByUsernameAndHomeId(username, id);
		}
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/byHomeId/{id}")
	@Override
	public ResponseEntity<List<TaskDTO>> getTasksByHomeId(@PathVariable("id") Long id) {
		Log.info("Searching all task from house with id: {}", id);
		List<TaskDTO> tasks = new ArrayList<>();
		tasks = taskService.getTasksByHomeId(id);
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PutMapping("/{id}/finished")
	@Override
	public ResponseEntity<?> updateFinishedStatus(Long id, @RequestBody boolean finished){
		Log.info("Updating the finished status value for task with id: {}", id);
		taskService.updateTaskFinished(id, finished);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<?> deleteTask(@PathVariable("id") Long id){
		Log.info("Deleting the task with id: {}", id);
		HttpStatus status = HttpStatus.OK;
		try{
			taskService.deleteTask(id);
		}catch(TasksException e){
			Log.info("ERR deleting task", e);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/home/{id}/weeklyTasks")
	@Override
	public ResponseEntity<List<Task>> getWeeklyTasksByHomeId(Long id) {
		Log.info("Getting all the weekly tasks of the home with id: {}", id);
		HttpStatus status = HttpStatus.OK;
		List<Task> weeklyTasks = new ArrayList<>();
		try{
			weeklyTasks = taskService.findAllByIdHomeAndWeeklyTaskTrue(id);
		}catch(TasksException e){
			Log.info("Error getting the weekly tasks of the home with id:  {}", id);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(weeklyTasks, status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PostMapping("/modifyTaskProgress")
	@Override
	public ResponseEntity<Boolean> modifyTaskProgress(@RequestBody ConfirmedTaskDTO confirmedTaskDTO){
		Log.info("Starting the task with id: {}", confirmedTaskDTO.getIdTask());
		HttpStatus status = HttpStatus.OK;
		boolean taskFinished = false; // BOOL QUE INDICA SI LA TAREA HA SIDO CONFIRMADA POR TODOS LOS MIEMBROS
		try{
			taskFinished = taskService.taskTrakingProcess(
					confirmedTaskDTO
			);
		}catch (TasksException e){
			Log.info("Error starting the progress of the task : {}", confirmedTaskDTO.getIdTask());
			status = HttpStatus.BAD_REQUEST;
		}
		Log.info("Ending the modify task progress");
		return new ResponseEntity<>(taskFinished, status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/checkTaskTraking")
	@Override
	public ResponseEntity<TaskTrakingStatusDTO> checkTaskTraking(String username) {
		Log.info("Checking the traking of the tasks who {} must confirm", username);
		HttpStatus status = HttpStatus.OK;
		TaskTrakingStatusDTO taskTrakingStatusDTO = null;
		try {
			taskTrakingStatusDTO = taskService.checkTaskTraking(username);
		} catch (TasksException e){
			Log.info("Error checking the task traking of the tasks who {} must confirm", username);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(taskTrakingStatusDTO, status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/checkTaskTrakingOfMyTasks")
	@Override
	public ResponseEntity<TaskTrakingStatusDTO> checkTaskTrakingOfMyTasks(String username) {
		Log.info("Checking the traking of the tasks who me {} have responsabilities", username);
		HttpStatus status = HttpStatus.OK;
		TaskTrakingStatusDTO taskTrakingStatusDTO = null;
		try {
			taskTrakingStatusDTO = taskService.checkTaskTrakingOfMyTasks(username);
		} catch (TasksException e){
			Log.info("Error checking the task traking of the tasks who {} must confirm", username);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(taskTrakingStatusDTO, status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/checkAllTaskAreConfirmed")
	@Override
	public ResponseEntity<Boolean> checkAllTaskAreConfirmed(List<Long> ids) {
		Log.info("Cheking if all weekly tasks are confirmed");
		HttpStatus status = HttpStatus.OK;
		boolean allConfirmed = false;
		try {
			allConfirmed = taskService.checkAllTaskAreConfirmed(ids);
		} catch (TasksException e) {
			Log.info("Error checking if all weekly task are confirmed");
		}
		return new ResponseEntity<>(allConfirmed, status);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PostMapping("/restartWeeklyTasks")
	@Override
	public ResponseEntity<?> restartWeeklyTasks(@RequestBody List<Long> ids) {
		Log.info("Restarting the traking of the weekly tasks");
		HttpStatus status = HttpStatus.OK;
		taskService.restartWeeklyTasks(ids);
		return new ResponseEntity<>(status);
	}


}
