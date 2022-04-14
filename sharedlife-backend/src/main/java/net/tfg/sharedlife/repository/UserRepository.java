package net.tfg.sharedlife.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
