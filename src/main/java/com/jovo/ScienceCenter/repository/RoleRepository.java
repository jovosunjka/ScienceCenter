package com.jovo.ScienceCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovo.ScienceCenter.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
}
