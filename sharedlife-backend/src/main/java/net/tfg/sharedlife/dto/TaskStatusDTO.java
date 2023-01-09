package net.tfg.sharedlife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskStatusDTO {
    private Long idTask;

    private boolean confirmed;
}
