package net.tfg.sharedlife.controller.auth;

import java.util.HashSet;
import java.util.Set;

import net.tfg.sharedlife.dto.PasswordUpdateDTO;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.service.home.HomeServiceImpl;
import net.tfg.sharedlife.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import net.tfg.sharedlife.dto.JwtDto;
import net.tfg.sharedlife.dto.LoginUserDto;
import net.tfg.sharedlife.dto.NewUserDto;
import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.model.Role;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.security.jwt.JwtProvider;
import net.tfg.sharedlife.service.role.RoleService;
import net.tfg.sharedlife.service.user.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthControllerImpl implements AuthController{
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	MailService mailService;

	private final static Logger logger = LoggerFactory.getLogger(AuthControllerImpl.class);

	@Override
	@PostMapping("/register")
	public ResponseEntity<?> register(NewUserDto newUser, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity("campos mal puestos", HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity("Ese nombre ya existe", HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity("Ese EMAIL ya existe", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(),
									  passwordEncoder.encode(newUser.getPassword()), newUser.getUsername());
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleEnum.ROLE_USER).get()); // Por defecto los usuarios van a ser USUARIO
		if(newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleName(RoleEnum.ROLE_ADMIN).get());
		}
		user.setRoles(roles);
		userService.createUser(user);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(LoginUserDto loginUser, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity("campos mal puestos", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}

	@Override
	@PutMapping("/updatePassword")
	public ResponseEntity<Boolean> updatePassword(String email) {
		logger.info("Starting the process of recovery the password");
		Boolean resultado = false;
		HttpStatus status = HttpStatus.OK;
		try {
			resultado = mailService.sendGmail(email);
		} catch (DataIncorrectException e){
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(resultado, status);
	}

	@Override
	public ResponseEntity<Boolean> newPassword(PasswordUpdateDTO passwordUpdateDTO) {
		logger.info("Starting the process of creation a new password");
		Boolean result = false;
		HttpStatus status = HttpStatus.OK;
		try {
			result = userService.registerNewPassword(
					passwordUpdateDTO.getEmail(),
					passwordUpdateDTO.getActualPassword(),
					passwordUpdateDTO.getNewPassword()
			);
		} catch (DataIncorrectException e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(result, status);
	}


}
