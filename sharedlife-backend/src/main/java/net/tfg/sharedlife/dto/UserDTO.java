package net.tfg.sharedlife.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private  Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Set<String> roles = new HashSet<>();
}
