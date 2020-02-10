package com.jovo.ScienceCenter.repository;


import java.util.List;
import java.util.Optional;

import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.ScientificArea;
import com.jovo.ScienceCenter.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;;


public interface UserDataRepository extends JpaRepository<UserData, Long> {
	
	Optional<UserData> findByCamundaUserId(String camundaUserId);
	boolean existsByCamundaUserId(String camundaUserId);

	@Query("SELECT ud FROM UserData ud WHERE ud.userStatus = 0 and :scientific_area MEMBER OF ud.scientificAreas")
	Optional<UserData> findByScientificArea(@Param("scientific_area") ScientificArea scientificArea);
}
