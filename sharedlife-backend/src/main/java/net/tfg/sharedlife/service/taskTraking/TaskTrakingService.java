package net.tfg.sharedlife.service.taskTraking;

import net.tfg.sharedlife.dto.TaskTrakingStatusDTO;
import net.tfg.sharedlife.model.*;

import java.util.List;

public interface TaskTrakingService {
    TaskTraking initiateTaskTraking();
    boolean taskTrakingProcess(Task task, String username, List<User> renters);

    TaskTrakingStatusDTO checkTaskTraking(User user);

    TaskTrakingStatusDTO checkTaskTrakingOfMyTasks(User user);

    boolean checkAllTaskAreConfirmed(List<Long> ids);

    void deleteTaskTraking(Task t);

    void saveTaskTraking(TaskTraking taskTraking);

    void updateTaskTrakingResponsability(TaskTrakingsUsers taskTrakingsUsers, User user);
}
