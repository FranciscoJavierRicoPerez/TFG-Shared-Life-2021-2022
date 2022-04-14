package net.tfg.sharedlife.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.tfg.sharedlife.model.User;

public class AuthUser implements UserDetails{
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	
	public AuthUser(String firstName, String lastName, String email, String password, String username,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.authorities = authorities;
	}
	
	public static AuthUser build(User user) {
		List<GrantedAuthority> authorities = 
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
		return new AuthUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUsername(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	
}
