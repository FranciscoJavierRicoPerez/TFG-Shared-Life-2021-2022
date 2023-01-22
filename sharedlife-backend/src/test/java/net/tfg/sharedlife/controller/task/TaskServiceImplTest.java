package net.tfg.sharedlife.controller.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.model.Task;
import net.tfg.sharedlife.repository.TaskRepository;
import net.tfg.sharedlife.service.task.TaskServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void deleteTaskSuccess(){
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        doAnswer(answer -> {return "delete";}).when(taskRepository).delete(Mockito.any());
        Assertions.assertDoesNotThrow(() -> taskServiceImpl.deleteTask(1L));
    }

    //@Test => TEST A REVISAR
    public void deleteTaskException(){
        Task task = null;
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        TasksException e = Assertions.assertThrows(
            TasksException.class, () -> {
            taskServiceImpl.deleteTask(Mockito.anyLong());
        });
        assertEquals(e.getMessage(), "ERR_NOT_FOUND");
    }


}
