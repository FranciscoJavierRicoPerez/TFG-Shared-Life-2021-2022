package net.tfg.sharedlife.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.model.User;

public interface UserController {

	User createUser(@RequestBody User user);

	public List<User> getAllUsers();
}
