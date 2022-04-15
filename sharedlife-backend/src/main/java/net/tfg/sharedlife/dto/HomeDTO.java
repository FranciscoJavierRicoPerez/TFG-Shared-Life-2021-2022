package net.tfg.sharedlife.dto;

import lombok.Data;

@Data
public class HomeDTO {
	private String address;
	private String floor;
	private String number;
	private String city;
	private String country;
	private String rooms;
	private String token;
}
