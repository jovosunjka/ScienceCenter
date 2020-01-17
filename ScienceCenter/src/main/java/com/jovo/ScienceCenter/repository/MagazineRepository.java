package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long>{
	
	Optional<Magazine> findByName(String name);

	boolean existsByName(String name);

	boolean existsByIssn(String issn);

	List<Magazine> findByMagazineStatus(Status magazineStatus);
}
