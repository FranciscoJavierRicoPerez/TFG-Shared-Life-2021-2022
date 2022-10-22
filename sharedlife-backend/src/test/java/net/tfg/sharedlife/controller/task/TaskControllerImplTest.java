package net.tfg.sharedlife.controller.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.tfg.sharedlife.exception.TasksException;
import net.tfg.sharedlife.service.task.TaskServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TaskControllerImplTest {
    
    @InjectMocks
    private TaskControllerImpl taskController;

    @Mock
    private TaskServiceImpl taskService;

    @Test
    public void deleteTaskOk() throws TasksException{
        HttpStatus ok = HttpStatus.OK;
        doAnswer(i -> {return null;}).when(taskService).deleteTask(Mockito.anyLong());
        ResponseEntity res_real = taskController.deleteTask(1L);
        assertEquals(ok, res_real.getStatusCode());
    }

    @Test
    public void deleteTaskException() throws TasksException{
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        doThrow(TasksException.class).when(taskService).deleteTask(Mockito.anyLong());
        ResponseEntity res_real = taskController.deleteTask(1L);
        assertEquals(badRequest, res_real.getStatusCode());
    }

}
