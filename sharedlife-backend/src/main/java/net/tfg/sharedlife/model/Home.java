package net.tfg.sharedlife.model;

import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * The Class Home.
 */
@Data
@Entity
@Table(name = "homes")
public class Home {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The address. */
	@Column(name = "address")
	private String address;
	
	/** The number. */
	@Column(name = "number")
	private String number;
	
	/** The floor. */
	@Column(name = "floor")
	private String floor;
	
	/** The city. */
	@Column(name = "city")
	private String city;
	
	/** The country. */
	@Column(name = "country")
	private String country;
	
	/** The rooms. */
	@Column(name = "rooms")
	private String rooms;
	
	@Column(name = "actualMemberCount")
	private Integer actualMemberCount;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "home_user", joinColumns = @JoinColumn(name = "home_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "home")
	private List<Task> tasks;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "home")
	private List<Spent> spents;
	

	/**
	 * Instantiates a new home.
	 */
	public Home() {}
	
	/**
	 * Instantiates a new home.
	 *
	 * @param address the address
	 * @param number the number
	 * @param floor the floor
	 * @param city the city
	 * @param country the country
	 * @param rooms the rooms
	 */
	public Home(String address, String number, String floor, String city, String country, String rooms) {
		super();
		this.address = address;
		this.number = number;
		this.floor = floor;
		this.city = city;
		this.country = country;
		this.rooms = rooms;
	}

}
