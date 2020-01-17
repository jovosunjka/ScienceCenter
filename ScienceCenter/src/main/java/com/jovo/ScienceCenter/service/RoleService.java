package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Role;

import java.util.Set;

public interface RoleService {

    Role getRole(String name);
    Set<String> getPermissions(String roleName);
}
