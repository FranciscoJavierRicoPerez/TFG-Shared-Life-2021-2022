package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.TaskTraking;

@Repository
public interface TaskTrakingRepository extends JpaRepository<TaskTraking, Long>{}
