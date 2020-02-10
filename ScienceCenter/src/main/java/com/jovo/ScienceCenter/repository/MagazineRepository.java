package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.Status;
import com.jovo.ScienceCenter.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long>{
	
	Optional<Magazine> findByName(String name);

	boolean existsByName(String name);

	boolean existsByIssn(String issn);

	List<Magazine> findByMagazineStatus(Status magazineStatus);

	@Query("SELECT m FROM Magazine m WHERE m.magazineStatus = 0 and (m.mainEditor = :editor or :editor MEMBER OF m.editors)")
	List<Magazine> findAllActivatedMagazinesByEditor(@Param("editor") UserData editor);
}
