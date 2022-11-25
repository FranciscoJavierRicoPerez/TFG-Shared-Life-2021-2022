package net.tfg.sharedlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    //List<Task> findAllByIdHomeAndWeeklyTaskTrue(Long id);
}
