package net.tfg.sharedlife.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name="tasks_traking")
public class TaskTraking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "startDate")
    private Date startDate;
    @Column(name = "endDate")
    private Date endDate;
    @OneToMany(mappedBy = "taskTraking")
    private Set<TaskTrakingsUsers> taskTrakingsUsers = new HashSet<>();
}