package net.tfg.sharedlife.service.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.model.Task;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.TaskRepository;
import net.tfg.sharedlife.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService{

	private final static Logger Log = LoggerFactory.getLogger(TaskServiceImpl.class);

	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void createTask(TaskDTO taskdto) {
		Task task = new Task();
		task.setTitle(taskdto.getTitle());
		task.setDescription(taskdto.getDescription());
		task.setStartDate(new Date());
		User user = userRepository.getByUsername(taskdto.getUser()).get();
		task.setUser(user);
		task.setFinished(false);
		taskRepository.save(task);
		Log.info("New task created succesfully");
	}

}
