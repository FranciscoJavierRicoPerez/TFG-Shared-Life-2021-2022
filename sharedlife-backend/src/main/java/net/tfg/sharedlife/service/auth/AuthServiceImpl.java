package net.tfg.sharedlife.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.tfg.sharedlife.dto.AuthUser;
import net.tfg.sharedlife.model.User;
import net.tfg.sharedlife.service.user.UserService;

@Service
public class AuthServiceImpl implements UserDetailsService{
	private final static Logger Log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Log.info("Loading info of the user with username: {}", username);
		User user = userService.getByUsername(username).get();
		return AuthUser.build(user);
	}

}
