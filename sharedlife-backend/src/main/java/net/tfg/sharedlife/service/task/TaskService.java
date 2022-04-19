package net.tfg.sharedlife.service.task;

import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.TaskDTO;

public interface TaskService {
	void createTask(@RequestBody TaskDTO task);
}
