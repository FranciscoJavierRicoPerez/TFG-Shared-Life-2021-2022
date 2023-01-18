package net.tfg.sharedlife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.tfg.sharedlife.model.User;

@Getter
@Setter
@ToString
public class VerifyPaidDTO {
    UserDTO user;
    boolean paid;
}
