package net.tfg.sharedlife.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "debts")
public class Debt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "idHome")
	private Long idHome;
	
	@Column(name = "idUser")
	private Long idUser; // ID DEL USUARIO QUE TIENE QUE PAGAR LA DEUDA
	
	@Column(name = "idSpent")
	private Long idSpent;
	
	@Column(name = "pricePerPerson")
	private double pricePerPerson;
	
	@Column(name = "paid")
	private boolean paid;
	
	@Column(name = "userToPay")
	private String userToPay;

}
