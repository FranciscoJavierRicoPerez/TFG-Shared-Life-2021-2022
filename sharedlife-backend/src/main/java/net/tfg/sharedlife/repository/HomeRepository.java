package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Home;

/**
 * The Interface HomeRepository.
 */
@Repository
public interface HomeRepository extends JpaRepository<Home, Long>{}
