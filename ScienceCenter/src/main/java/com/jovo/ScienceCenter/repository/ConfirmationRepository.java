package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long>{
	
	Optional<Confirmation> findByToken(String token);
}
