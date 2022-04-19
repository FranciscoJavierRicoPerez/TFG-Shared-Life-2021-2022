package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tfg.sharedlife.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{}
