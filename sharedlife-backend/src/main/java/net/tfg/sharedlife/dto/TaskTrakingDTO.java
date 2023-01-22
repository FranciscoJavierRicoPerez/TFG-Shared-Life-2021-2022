package net.tfg.sharedlife.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class TaskTrakingDTO {
    private Long id;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
}
