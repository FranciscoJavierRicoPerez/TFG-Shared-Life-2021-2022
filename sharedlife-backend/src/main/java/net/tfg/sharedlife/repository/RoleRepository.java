package net.tfg.sharedlife.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.enums.RoleEnum;
import net.tfg.sharedlife.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByRoleName(RoleEnum role);
}
