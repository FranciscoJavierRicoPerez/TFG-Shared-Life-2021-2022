package net.tfg.sharedlife.service.task;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskService {
	void createTask(@RequestBody TaskDTO task);
	
	List<TaskDTO> getTasksByUsername(String username);
	
	List<TaskDTO> getTasksByHomeIdAndUsername(Long id, String username);
	
	void updateTaskFinished(Long id, boolean finished);
}
