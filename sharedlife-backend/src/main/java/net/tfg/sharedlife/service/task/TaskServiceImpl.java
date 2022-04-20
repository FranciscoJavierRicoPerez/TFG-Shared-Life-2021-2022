package net.tfg.sharedlife.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.dto.TaskDTO;
import net.tfg.sharedlife.model.Task;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.repository.HomeRepository;
import net.tfg.sharedlife.repository.TaskRepository;
import net.tfg.sharedlife.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService{

	private final static Logger Log = LoggerFactory.getLogger(TaskServiceImpl.class);

	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HomeRepository homeRepository;
	
	@Override
	public void createTask(TaskDTO taskdto) {
		Task task = new Task();
		task.setTitle(taskdto.getTitle());
		task.setDescription(taskdto.getDescription());
		task.setStartDate(new Date());
		User user = userRepository.getByUsername(taskdto.getUser()).get();
		task.setUser(user);
		task.setFinished(false);
		task.setHome(homeRepository.getById(Long.parseLong(taskdto.getIdHome())));
		taskRepository.save(task);
		Log.info("New task created succesfully");
	}

	@Override
	public List<TaskDTO> getTasksByUsername(String username) {
		Log.info("Getting all tasks for user with username: {}", username);
		List<TaskDTO> tasksdto = new ArrayList<>();
		List<User> users = userRepository.findAll();
		User user = new User();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				user = u;
			}
		}
		List<Task> tasks = user.getTasks();
		for(Task t : tasks) {
			TaskDTO taskdto = new TaskDTO();
			taskdto.setTitle(t.getTitle());
			taskdto.setDescription(t.getDescription());
			taskdto.setStartDate(t.getStartDate());
			taskdto.setEndDate(t.getEndDate());
			taskdto.setUser(t.getUser().getUsername());
			taskdto.setFinished(t.isFinished());
			taskdto.setIdHome(t.getHome().getId().toString());
			tasksdto.add(taskdto);
		}
		return tasksdto;
	}

	@Override
	public List<TaskDTO> getTasksByHomeIdAndUsername(Long id, String username) {
		Log.info("Getting all tasks for home with id: {}", id);
		List<TaskDTO> tasksdto = new ArrayList<>();
		List<Task> tasks = taskRepository.findAll();
		for(Task t : tasks) {
			if(t.getHome().getId().equals(id) && !t.getUser().getUsername().equals(username)) {
				TaskDTO taskdto = new TaskDTO();
				taskdto.setTitle(t.getTitle());
				taskdto.setDescription(t.getDescription());
				taskdto.setStartDate(t.getStartDate());
				taskdto.setEndDate(t.getEndDate());					
				taskdto.setUser(t.getUser().getUsername());
				taskdto.setFinished(t.isFinished());
				taskdto.setIdHome(t.getHome().getId().toString());
				tasksdto.add(taskdto);
			}
		}
		return tasksdto;
	}
}
