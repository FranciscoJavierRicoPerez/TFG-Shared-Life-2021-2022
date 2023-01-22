package net.tfg.sharedlife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.tfg.sharedlife.model.User;

import java.util.List;

@Getter
@Setter
@ToString
public class ConfirmedTaskDTO {
    private String username;
    private Long idTask;
    private List<UserDTO> members;
}
