package com.jovo.ScienceCenter.repository;


import java.util.Optional;

import com.jovo.ScienceCenter.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;;


public interface UserDataRepository extends JpaRepository<UserData, Long> {
	
	Optional<UserData> findByCamundaUserId(String camundaUserId);
	boolean existsByCamundaUserId(String camundaUserId);
}
