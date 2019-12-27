package com.jovo.ScienceCenter.service;

import java.util.Set;

public interface RoleService {
	
    Set<String> getPermissions(String roleName);
}
