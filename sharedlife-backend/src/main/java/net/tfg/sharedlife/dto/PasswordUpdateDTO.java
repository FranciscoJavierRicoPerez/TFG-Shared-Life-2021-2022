package net.tfg.sharedlife.dto;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String email;
    private String actualPassword;
    private String newPassword;

}
