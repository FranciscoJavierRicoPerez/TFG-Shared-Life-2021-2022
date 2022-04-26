package net.tfg.sharedlife.dto;

import lombok.Data;

@Data
public class DebtDTO {
	private Long id;
	private Long idHome;
	private Long idUser; // ID DEL USUARIO QUE TIENE QUE PAGAR LA DEUDA
	private Long idSpent;
	private double pricePerPerson;
	private boolean paid;
	private String userToPay;
}
