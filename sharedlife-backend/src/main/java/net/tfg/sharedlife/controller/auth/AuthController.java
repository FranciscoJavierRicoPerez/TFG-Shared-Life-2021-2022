package net.tfg.sharedlife.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.tfg.sharedlife.dto.JwtDto;
import net.tfg.sharedlife.dto.LoginUserDto;
import net.tfg.sharedlife.dto.NewUserDto;

public interface AuthController {

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody NewUserDto newUser, BindingResult bindingResult);
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@RequestBody LoginUserDto loginUser, BindingResult bindingResult);

	@PutMapping("/updatePassword")
	ResponseEntity<Boolean> updatePassword(@RequestBody String email);
}
