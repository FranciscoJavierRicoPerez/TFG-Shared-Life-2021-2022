package net.tfg.sharedlife.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Task;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    //List<Task> findAllByIdHomeAndWeeklyTaskTrue(Long id);


    @Query(value = "SELECT * FROM TASKS t WHERE t.id_task_traking=:id", nativeQuery = true)
    Task getTaskByTaskTrakingId(@PathVariable("id") Long id);
}
