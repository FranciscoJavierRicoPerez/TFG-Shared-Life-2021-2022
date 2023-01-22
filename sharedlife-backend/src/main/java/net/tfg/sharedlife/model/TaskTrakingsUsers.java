package net.tfg.sharedlife.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "TASK_TRAKINGS_USERS")
public class TaskTrakingsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "task_traking_id")
    private TaskTraking taskTraking;
    @Column(name = "confirmed")
    private boolean confirmed;
}