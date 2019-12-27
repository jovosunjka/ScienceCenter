package com.jovo.ScienceCenter.service;


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
import com.jovo.ScienceCenter.model.User;

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
        User user = userService.getUser(username);

        if(user == null) {
            String message = String.format("No user found with username '%s' in database.", username);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }
       
        Collection<? extends GrantedAuthority> garantedAuthorities = mapRolesToAuthorities(user.getRoles());
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), garantedAuthorities);
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
