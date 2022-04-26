package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Debt;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>{}
