package net.tfg.sharedlife.dto;

import lombok.Data;

@Data
public class SpentDTO {
	private Long id;
	private String title;
	private String description;
	private double totalPrice;
	private String userToPay;
	private String idHome;
}
