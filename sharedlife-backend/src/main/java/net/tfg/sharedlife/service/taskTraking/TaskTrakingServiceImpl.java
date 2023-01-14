package net.tfg.sharedlife.service.taskTraking;

import java.util.*;

import net.tfg.sharedlife.dto.TaskStatusDTO;
import net.tfg.sharedlife.dto.TaskTrakingStatusDTO;
import net.tfg.sharedlife.model.*;
import net.tfg.sharedlife.repository.TaskRepository;
import net.tfg.sharedlife.repository.TaskTrakingsUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.repository.TaskTrakingRepository;

import javax.transaction.Transactional;

@Service
public class TaskTrakingServiceImpl implements TaskTrakingService {

    private final static Logger Log = LoggerFactory.getLogger(TaskTrakingServiceImpl.class);

    @Autowired
    TaskTrakingRepository taskTrakingRepository;

    @Autowired
    TaskTrakingsUsersRepository taskTrakingsUsersRepository;

    @Autowired
    TaskRepository taskRepository;

    //@Autowired
    //HomeService homeService;

    @Override
    public TaskTraking initiateTaskTraking() {
        Log.info("Starting the traking of the task");
        TaskTraking taskTraking = new TaskTraking();
        taskTraking.setCreationDate(new Date());
        return taskTraking;
    }

    @Override
    @Transactional
    public boolean taskTrakingProcess(Task task, String username, List<User> renters) {
        Log.info("Task traking process");
        boolean taskFinished = false;
        TaskTraking taskTraking = task.getTaskTraking();
        if(taskTraking.getStartDate() == null) {
            taskTraking.setStartDate(new Date());
        } else {
            if(taskTraking.getEndDate() == null){
                taskTraking.setEndDate(new Date());
                for(User renter : renters){
                    if(!renter.getUsername().equals(username) && renter.getRoles().size() < 2){
                        TaskTrakingsUsers taskTrakingsUsers = new TaskTrakingsUsers();
                        taskTrakingsUsers.setTaskTraking(taskTraking);
                        taskTrakingsUsers.setUser(renter);
                        taskTrakingsUsers.setConfirmed(false);
                        taskTrakingsUsersRepository.save(taskTrakingsUsers);
                    }
                }
            } else {
                //if(!taskTraking.isConfirmed()){
                //    taskTraking.setConfirmed(true);
                //}
                System.out.println("CONFIRMACION");
                // Para la confirmacion lo que hay que hacer es obtener el tasktrakingsusers asociado a la tarea task o
                // al usuario que hace la confirmacion
                for(User user : renters){
                    if(user.getUsername().equals(username)){ // es el usuario que ha confirmado la tarea
                        // REVISAR ESTA LLAMADA AL REPOSITORIO POR QUE PUEDE EXISTIR MAS DE UNO
                        List<TaskTrakingsUsers> taskTrakingsUsersList = taskTrakingsUsersRepository.findAllByUserId(user.getId());
                        for(TaskTrakingsUsers taskTrakingsUsers : taskTrakingsUsersList){
                            if(taskTrakingsUsers.getTaskTraking().getId().equals(taskTraking.getId())) {
                                taskTrakingsUsers.setConfirmed(true);
                                taskTrakingsUsersRepository.save(taskTrakingsUsers);
                            }
                        }
                        break;
                    }
                }
            }
        }
        taskTrakingRepository.save(taskTraking);
        return taskFinished;
    }

    @Override
    public TaskTrakingStatusDTO checkTaskTraking(User user) {
        TaskTrakingStatusDTO taskTrakingStatusDTO = new TaskTrakingStatusDTO();
        List<TaskTrakingsUsers> taskTrakingsUsersList = taskTrakingsUsersRepository.findAllByUserId(user.getId());
        List<TaskStatusDTO> taskStatusDTOList = new ArrayList<>();
        for(TaskTrakingsUsers taskTrakingsUsers : taskTrakingsUsersList){
            TaskStatusDTO taskStatusDTO = new TaskStatusDTO();
            taskStatusDTO.setConfirmed(taskTrakingsUsers.isConfirmed());
            // NECESITO EL ID DE LA TAREA ASOCIADO AL TASK TRAKING DENTRO DE TASK TRAKING USERS
            // LO MISMO ESTO DA PROBLEMAS NO LO SE +++++
            Task t = taskRepository.getTaskByTaskTrakingId(taskTrakingsUsers.getTaskTraking().getId());
            taskStatusDTO.setIdTask(t.getId());
            taskStatusDTOList.add(taskStatusDTO);
        }
        taskTrakingStatusDTO.setTaskStatusDTOList(taskStatusDTOList);
        return taskTrakingStatusDTO;
    }

    @Override
    public TaskTrakingStatusDTO checkTaskTrakingOfMyTasks(User user) {
        TaskTrakingStatusDTO  taskTrakingStatusDTO = new TaskTrakingStatusDTO();
        List<TaskStatusDTO> taskStatusDTOList = new ArrayList<>();
        List<Task> tasks = user.getTasks();
        for(Task t : tasks){
            if(t.getWeekTask()){
                TaskStatusDTO taskStatusDTO = new TaskStatusDTO();
                TaskTraking taskTraking = t.getTaskTraking(); // Aqui tengo el traking de la tare
                List<TaskTrakingsUsers> taskTrakingsUsersList =
                        taskTrakingsUsersRepository.findAllByTaskTrakingId(taskTraking.getId()); // Obtener todos los trakingsUsers apartir del id de traking
                int count = 0;
                for(TaskTrakingsUsers taskTrakingsUsers : taskTrakingsUsersList) {
                    if(taskTrakingsUsers.isConfirmed()){
                        count++;
                    }
                }
                taskStatusDTO.setIdTask(t.getId());
                if(count == taskTrakingsUsersList.size()){
                    taskStatusDTO.setConfirmed(true);
                } else {
                    taskStatusDTO.setConfirmed(false);
                }
                taskStatusDTOList.add(taskStatusDTO);
            }
        }
        taskTrakingStatusDTO.setTaskStatusDTOList(taskStatusDTOList);
        return taskTrakingStatusDTO;
    }

    @Override
    public boolean checkAllTaskAreConfirmed(List<Long> ids) {
        boolean allConfirmed = false;
        int count = 0;
        for(Long id : ids){
            Task t = taskRepository.findById(id).get();
            TaskTraking traking = t.getTaskTraking();
            List<TaskTrakingsUsers> taskTrakingsUsersList =
                    taskTrakingsUsersRepository.findAllByTaskTrakingId(traking.getId());
            int count2 = 0;
            for(TaskTrakingsUsers taskTrakingsUsers : taskTrakingsUsersList){
                if(taskTrakingsUsers.isConfirmed()){
                    count2++;
                }
            }
            if(count2 == taskTrakingsUsersList.size()){
                count++;
            }
        }
        if(count == ids.size()){
            allConfirmed = true;
        }
        return allConfirmed;
    }

    @Override
    public void deleteTaskTraking(Task t) {
        TaskTraking taskTraking = t.getTaskTraking();
        List<TaskTrakingsUsers> taskTrakingsUsersList =
                taskTrakingsUsersRepository.findAllByTaskTrakingId(taskTraking.getId());
        for(TaskTrakingsUsers taskTrakingsUser : taskTrakingsUsersList){
            taskTrakingsUsersRepository.deleteById(taskTrakingsUser.getId());
        }
        taskTrakingRepository.delete(taskTraking);
        t.setTaskTraking(null);
        taskRepository.save(t);
    }

    @Override
    public void saveTaskTraking(TaskTraking taskTraking) {
        taskTrakingRepository.save(taskTraking);
    }
}
