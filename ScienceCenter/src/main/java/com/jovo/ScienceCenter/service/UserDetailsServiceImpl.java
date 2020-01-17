package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jovo.ScienceCenter.model.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Primary // Intellij pronalazi vise implementacija za UserDetailService,
// zato koristimo ovu anotaciju, da bi ova implementacija imala prednost u odnosu na druge,
// i da bi @Autowired znao koju implementaciju da izabere
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
    @Autowired
    private UserService userService;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.camunda.bpm.engine.identity.User camundaUser = userService.getUser(username);

        if(camundaUser == null) {
            String message = String.format("No user found with username '%s' in database.", username);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }

        UserData userData = userService.getUserData(camundaUser.getId());

        if(userData == null) {
            String message = String.format("No user data found with camundaUserId '%s' in database.", camundaUser.getId());
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }

        if(userData.getUserStatus() != Status.ACTIVATED) {
            String message = String.format("User (camundaUserId=%s) is not activated.", camundaUser.getId());
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }
       
        Collection<? extends GrantedAuthority> garantedAuthorities = mapRolesToAuthorities(userData.getRoles());

        String password = camundaUser.getPassword().replace("{bcrypt}", "");
        return new org.springframework.security.core.userdetails.User(camundaUser.getId(), password, garantedAuthorities);
    }

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> garantedAuthorities = new HashSet<GrantedAuthority>();

        roles.stream()
                .forEach(role -> {
                    Set<GrantedAuthority> simpleGrantedAuthorities = role.getPermissions().stream()
                    .map(per -> new SimpleGrantedAuthority(per.getName()))
                    .collect(Collectors.toSet());
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                    garantedAuthorities.addAll(simpleGrantedAuthorities);
                });

        return garantedAuthorities;
    }
}
