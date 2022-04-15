package net.tfg.sharedlife.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * The Class User.
 */
@Data
@Entity
@Table(name = "users")
public class User {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The first name. */
	@Column(name = "firstName")
	private String firstName;
	
	/** The last name. */
	@Column(name = "lastName")
	private String lastName;
	
	/** The email. */
	@Column(name = "email")
	private String email;
	
	/** The password. */
	@Column(name = "password")
	private String password;
	
	/** The username. */
	@Column(name = "username")
	private String username;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_home", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "home_id"))
	private Set<Home> homes = new HashSet<>();*/
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.username = username;
	}

}
