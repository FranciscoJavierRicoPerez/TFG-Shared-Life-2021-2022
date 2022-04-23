package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Spent;

@Repository
public interface SpentRepository extends JpaRepository<Spent, Long>{}
