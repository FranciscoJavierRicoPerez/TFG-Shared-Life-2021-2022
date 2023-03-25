package net.tfg.sharedlife.service.task;

import java.util.*;

import net.tfg.sharedlife.dto.*;
import net.tfg.sharedlife.model.*;
import net.tfg.sharedlife.service.home.HomeService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.enums.HomeRoomEnum;
import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.mapper.TaskTrakingMapper;
import net.tfg.sharedlife.repository.HomeRepository;
import net.tfg.sharedlife.repository.TaskRepository;
import net.tfg.sharedlife.repository.UserRepository;
import net.tfg.sharedlife.service.taskTraking.TaskTrakingService;
import net.tfg.sharedlife.service.user.UserService;

import javax.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService{

	private final static Logger Log = LoggerFactory.getLogger(TaskServiceImpl.class);

	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository; // borrar
	
	@Autowired
	private HomeRepository homeRepository; // borrar

	@Autowired 
	private UserService userService;

	@Autowired
	private TaskTrakingService taskTrakingService;

	@Autowired
	@Lazy
	private HomeService homeService;

	private TaskTrakingMapper taskTrakingMapper = Mappers.getMapper(TaskTrakingMapper.class);
	
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
		task.setHomeRoom(null);
		task.setWeekTask(false);
		taskRepository.save(task);
		Log.info("New task created succesfully");
	}

	@Override
	public List<TaskDTO> getTasksByUsernameAndHomeId(String username, Long id) {
		Log.info("Getting all tasks for user with username {} and home {}", username, id);
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
			if(t.getHome().getId().equals(id)) {
				TaskDTO taskdto = new TaskDTO();
				taskdto.setId(t.getId());
				taskdto.setTitle(t.getTitle());
				taskdto.setDescription(t.getDescription());
				taskdto.setStartDate(t.getStartDate());
				taskdto.setEndDate(t.getEndDate());
				taskdto.setUser(t.getUser().getUsername());
				taskdto.setFinished(t.isFinished());
				taskdto.setIdHome(t.getHome().getId().toString());
				taskdto.setWeekTask(t.getWeekTask());
				taskdto.setTaskTraking(taskTrakingMapper.taskTrakingToTaskTrakingDTO(t.getTaskTraking()));
				tasksdto.add(taskdto);
			}
		}
		return tasksdto;
	}

	@Override
	public List<TaskDTO> getTasksByHomeId(Long id) {
		Log.info("Getting all tasks for home with id: {}", id);
		List<TaskDTO> tasksdto = new ArrayList<>();
		List<Task> tasks = taskRepository.findAll();
		for(Task t : tasks) {
			if(t.getHome().getId().equals(id)) {
				TaskDTO taskdto = new TaskDTO();
				taskdto.setId(t.getId());
				taskdto.setTitle(t.getTitle());
				taskdto.setDescription(t.getDescription());
				taskdto.setStartDate(t.getStartDate());
				taskdto.setEndDate(t.getEndDate());					
				taskdto.setUser(t.getUser().getUsername());
				taskdto.setFinished(t.isFinished());
				taskdto.setIdHome(t.getHome().getId().toString());
				taskdto.setWeekTask(t.getWeekTask());
				taskdto.setTaskTraking(taskTrakingMapper.taskTrakingToTaskTrakingDTO(t.getTaskTraking()));
				tasksdto.add(taskdto);
			}
		}
		return tasksdto;
	}

	@Override
	public void updateTaskFinished(Long id, boolean finished) {
		Log.info("Update of the finished status of the task with id: {}", id);
		Task t = taskRepository.findById(id).get();
		t.setFinished(finished);
		t.setEndDate(new Date());
		taskRepository.save(t);
	}

	@Override
	public void deleteTask(Long id) throws TasksException{
		Log.info("Deleting the task with id: {}", id);
		Task task = taskRepository.findById(id).orElse(null);
		if(id == null || task == null){
			throw new TasksException("ERR_NOT_FOUND");
		}
		taskRepository.delete(task);
	}

	@Override
	public void createWeeklyTask(Home home) throws TasksException {
		Log.info("Creating all the weekly tasks");
		List<Task> weeklyTasks = new ArrayList<>();
		HomeRoomEnum [] rooms = {
			HomeRoomEnum.LIVING_ROOM, 
			HomeRoomEnum.BATHROOM, 
			HomeRoomEnum.KITCHEN, 
			HomeRoomEnum.HALLWAY
		};
		for(int i = 0; i < rooms.length; i++){
			Task task = new Task();
			switch(rooms[i]){
				case LIVING_ROOM:
					task.setTitle("LIMPIEZA DEL SALON");
					task.setDescription("Tarea semanal de limpieza del salon");
					break;
				case BATHROOM:
					task.setTitle("LIMPIEZA DEL CUARTO DE BAÑO");
					task.setDescription("Tarea semanal de limpieza del cuarto de baño");
					break;
				case KITCHEN:
					task.setTitle("LIMPIEZA DE LA COCINA");
					task.setDescription("Tarea semanal de limpieza de la cocina");
					break;
				case HALLWAY:
					task.setTitle("LIMPIEZA DEL PASILLO Y SACAR LA BASURA");
					task.setDescription("Tarea semanal de limpieza del pasillo y sacar la basura");
					break;
			}
			task.setHomeRoom(rooms[i]);
			task.setWeekTask(true);
			task.setStartDate(new Date());
			task.setFinished(false);
			Set<User> users = home.getUsers();
			boolean isAdmin;
			for(User u : users){
				isAdmin = false;
				for(Role role : u.getRoles()){
					if(role.getRoleName().equals(RoleEnum.ROLE_ADMIN)){
						isAdmin = true;
					}
				}
				if(!isAdmin){
					task.setUser(u);
				}
			}
			task.setHome(home);
			task.setTaskTraking(taskTrakingService.initiateTaskTraking());
			taskRepository.save(task);
			weeklyTasks.add(task);
		}
		home.setTasks(weeklyTasks);
		Log.info("Creating weekly tasks process terminate succesfully...");
	}

	@Override
	public List<Task> findAllByIdHomeAndWeeklyTaskTrue(Long idHome) throws TasksException {
		List<Task> tasks = new ArrayList<>();
		List<Task> weeklyTasks = new ArrayList<>();
		if(null == idHome){
			throw new TasksException("ID HOME NULL");
		}
 		tasks = taskRepository.findAll();
		for(Task task : tasks){
			if(task.getHome().getId().equals(idHome) && Boolean.TRUE.equals(task.getWeekTask())){
				weeklyTasks.add(task);
			}
		}
		return weeklyTasks;
	}

	@Transactional
	@Override
	public void updateTaskResponsabilities(User user,  List<Task> tasks) {
		for(Task task : tasks){
			task.setUser(user);
			// Tambien deberia comprobar si es una weeklyTask
			// y si es weekTask y no tiene un traking asociado debo crearlo
			if(task.getWeekTask()){
				if(task.getTaskTraking() == null) {
					TaskTraking taskTraking = taskTrakingService.initiateTaskTraking();
					taskTrakingService.saveTaskTraking(taskTraking);
					task.setTaskTraking(taskTraking);
				}
			}
			taskRepository.save(task);
		}
	}

	@Override
	public Task getTaskById(Long id) throws TasksException {
		if(taskRepository.findById(id).isEmpty()) {
			throw new TasksException("TASK_NOT_FOUND");
		}
		return taskRepository.findById(id).get();
	}

	@Override
	public boolean taskTrakingProcess(ConfirmedTaskDTO confirmedTaskDTO) throws TasksException {
		if(confirmedTaskDTO.getIdTask() == null){
			throw new TasksException("ID_TASK_NOT_EXIST");
		}
		List<User> renters = new ArrayList<>();
		/// ESTE FOR TIENE QUE CAMBIAR POR UN MAPPER O ALGO
		for(UserDTO u : confirmedTaskDTO.getMembers()){
			User user = new User();
			user.setId(u.getId());
			user.setEmail(u.getEmail());
			user.setUsername(u.getUsername());
			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setPassword(u.getPassword());
			Set<Role> roles = new HashSet<>();
			for(String role : u.getRoles()){
				if(role.equals("ROLE_ADMIN")){
					roles.add(new Role(RoleEnum.ROLE_ADMIN));
				} else {
					roles.add(new Role(RoleEnum.ROLE_USER));
				}
			}
			user.setRoles(roles);
			renters.add(user);
		}
		return taskTrakingService.taskTrakingProcess(getTaskById(confirmedTaskDTO.getIdTask()), confirmedTaskDTO.getUsername(), renters);
	}

	@Override
	public TaskTrakingStatusDTO checkTaskTraking(String username) throws TasksException {
		User user = userRepository.getByUsername(username).get();
		if(user == null){
			throw new TasksException("ERR_CHEKING_TASK_TRAKING");
		}
		return taskTrakingService.checkTaskTraking(user);
	}

	@Override
	public TaskTrakingStatusDTO checkTaskTrakingOfMyTasks(String username) throws TasksException {
		User user = userRepository.getByUsername(username).get();
		if(user == null){
			throw new TasksException("ERR_CHEKING_TASK_TRAKING");
		}
		return taskTrakingService.checkTaskTrakingOfMyTasks(user);
	}

	@Override
	public boolean checkAllTaskAreConfirmed(List<Long> ids) throws TasksException {
		if(ids.isEmpty()){
			throw new TasksException("ERR_NO_TASKS");
		}
		return taskTrakingService.checkAllTaskAreConfirmed(ids);
	}

	@Override
	public void restartWeeklyTasks(List<Long> ids) {
		for(Long id : ids){
			Task t = taskRepository.getById(id);
			// Para cada tarea tengo que borrar su taskTraking y su taskTrakingUsers
			// Empezamos con el taskTrakingUsers
			taskTrakingService.deleteTaskTraking(t);
			taskRepository.save(t);
			t.setUser(null);
			taskRepository.save(t);
		}
	}

	@Override
	public void deleteWeeklyTask(Task t) {
		if(t.getTaskTraking() != null){
			taskTrakingService.deleteTaskTraking(t);
		}
		taskRepository.delete(t);
	}
}
