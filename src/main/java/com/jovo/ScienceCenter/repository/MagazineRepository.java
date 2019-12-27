package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long>{
	
	Magazine findByName(String name);
}
