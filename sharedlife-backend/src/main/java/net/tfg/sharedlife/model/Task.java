package net.tfg.sharedlife.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.tfg.sharedlife.enums.HomeRoomEnum;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Titulo de la tarea
	@Column(name = "title")
	private String title;
	
	// Descripción de la tarea
	@Column(name = "description")
	private String description;
	
	//Fecha de inicio de la tarea
	@Column(name = "startDate")
	private Date startDate;
	
	//Fecha de fin de tarea
	@Column(name = "endDate")
	private Date endDate;
	
	// Indica si la tarea esta terminada o no
	@Column(name = "finished")
	private boolean finished;

	// Indica si la tarea es una tarea semana o no
	@Column(name = "weekTask")
	private Boolean weekTask;

	// Enumerado que indica si la tarea pertenece a una habitacion de la casa
	@Column(name = "homeRoom")
	private HomeRoomEnum homeRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user; // RESPONSABLE DE LA TAREA
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_home")
	private Home home;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_taskTraking")
	private TaskTraking taskTraking; 
}
