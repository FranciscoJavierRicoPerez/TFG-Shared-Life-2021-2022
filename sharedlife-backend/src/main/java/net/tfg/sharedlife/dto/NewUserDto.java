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
	/*public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}*/
	
}
