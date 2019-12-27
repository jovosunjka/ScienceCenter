package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.model.User;

public interface UserService {

	User getLoggedUser() throws Exception;
	
    void save(User user) throws Exception;

    User getUser(String username);
    
    User getUser(String username, String password);

    boolean exists(String username);

	boolean register(String username, String password, String repeatedPassword, String firstName, String lastName,
                     String email, String city, String country);

}
