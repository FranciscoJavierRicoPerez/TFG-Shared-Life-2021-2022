package net.tfg.sharedlife.dto;

import lombok.Data;

@Data
public class HomeDTO {
	private Long id;
	private String address;
	private String floor;
	private String number;
	private String city;
	private String country;
	private String rooms;
	private String token;
	private boolean completed;
	private String actualMemberCount;
}
