package net.tfg.sharedlife.repository;

import net.tfg.sharedlife.model.TaskTrakingsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTrakingsUsersRepository extends JpaRepository<TaskTrakingsUsers, Long> {
    TaskTrakingsUsers findByUserId(Long id);

    List<TaskTrakingsUsers> findAllByUserId(Long id);
}
