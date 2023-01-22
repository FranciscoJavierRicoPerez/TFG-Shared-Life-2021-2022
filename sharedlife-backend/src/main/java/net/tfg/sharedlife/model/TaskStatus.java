package net.tfg.sharedlife.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TASK_STATUS")
@Getter
@Setter
@ToString
public class TaskStatus {
    @Id
    @Column(name = "ID")
    private Long id;

    /* @JoinColumn(name = "task_traking_id")
    @OneToOne(fetch = FetchType.LAZY)
    private TaskTraking taskTraking; */
}
