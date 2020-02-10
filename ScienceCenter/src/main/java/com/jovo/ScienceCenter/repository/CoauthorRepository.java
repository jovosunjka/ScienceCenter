package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Coauthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoauthorRepository extends JpaRepository<Coauthor, Long>{
	
	Optional<Coauthor> findByEmail(String email);
}
