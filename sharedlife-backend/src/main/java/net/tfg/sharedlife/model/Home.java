package net.tfg.sharedlife.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Home.
 */
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor the new floor
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the rooms.
	 *
	 * @return the rooms
	 */
	public String getRooms() {
		return rooms;
	}

	/**
	 * Sets the rooms.
	 *
	 * @param rooms the new rooms
	 */
	public void setRooms(String rooms) {
		this.rooms = rooms;
	}
	
	
	
}
