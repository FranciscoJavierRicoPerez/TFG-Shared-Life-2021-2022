package net.tfg.sharedlife.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

// DTO que envia la informacion necesaria para la creación de una usuario
@Data
public class NewUserDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username;
	private Set<String> roles = new HashSet<>();
}
