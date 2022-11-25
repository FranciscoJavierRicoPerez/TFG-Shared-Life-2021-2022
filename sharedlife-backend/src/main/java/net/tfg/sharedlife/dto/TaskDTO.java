package net.tfg.sharedlife.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDTO {
	private Long id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private boolean finished;
	private boolean weekTask;
	private String homeRoom;
	private String user;
	private String idHome;
}
