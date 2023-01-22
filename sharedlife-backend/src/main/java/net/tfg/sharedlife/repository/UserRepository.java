package net.tfg.sharedlife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.User;

/**
 * The Interface UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> getByUsername(String userName);
	boolean existsByUsername(String userName);
	boolean existsByEmail(String email);
	
	@Query(value = "SELECT u.* FROM USER u, ROLE r, HOMES h, HOME_USER hu, WHERE h.id == :id AND h.id == hu.id AND hu.id == u.id AND ", nativeQuery = true)
	List<User> findAllMembersByHomeId(Long id);
}
