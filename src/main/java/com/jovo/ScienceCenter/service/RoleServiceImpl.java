package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Role;
import com.jovo.ScienceCenter.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Set<String> getPermissions(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if(role == null) return new HashSet<>();

        return role.getPermissions().stream()
                .map(per -> per.getName())
                .collect(Collectors.toSet());
    }
}
