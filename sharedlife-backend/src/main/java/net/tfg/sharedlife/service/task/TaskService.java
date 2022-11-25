package net.tfg.sharedlife.service.task;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.model.Home;
import net.tfg.sharedlife.model.Task;
import net.tfg.sharedlife.model.User;

public interface TaskService {
	void createTask(@RequestBody TaskDTO task);
	
	List<TaskDTO> getTasksByUsernameAndHomeId(String username, Long id);
	
	List<TaskDTO> getTasksByHomeId(Long id);
	
	void updateTaskFinished(Long id, boolean finished);

	void deleteTask(Long id) throws TasksException;

	void createWeeklyTask(Home home);

	//void manageWeeklyTaskOwner(Home home);

	List<Task> findAllByIdHomeAndWeeklyTaskTrue(Long idHome) throws TasksException;

	void updateTaskResponsabilities(User user, List<Task> tasks);

}
