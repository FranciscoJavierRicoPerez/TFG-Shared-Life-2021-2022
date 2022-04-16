package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long>{}
