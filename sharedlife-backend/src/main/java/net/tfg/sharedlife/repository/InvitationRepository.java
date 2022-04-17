package net.tfg.sharedlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long>{
	List<Invitation> findByUsername(String username);
} 
