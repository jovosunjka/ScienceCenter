package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.model.User;
import com.jovo.ScienceCenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public User getLoggedUser() throws Exception {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
					.getPrincipal();
			return userRepository.findByUsername(user.getUsername()).orElse(null);
		} catch (Exception e) {
			throw new Exception("User not found!");
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void save(User user) throws Exception {
		userRepository.save(user);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public User getUser(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password).orElse(null);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}


	@Override
	public boolean register(String username, String password, String repeatedPassword, String firstName, String lastName,
						String email, String city, String country) {
		if (username == null || password == null || repeatedPassword == null || firstName == null || lastName == null
				|| email == null || city == null || country == null)
			return false;
		if (username.equals("") || password.equals("") || repeatedPassword.equals("") || firstName.equals("") || lastName.equals("")
				|| email.equals("") || city.equals("") || country.equals(""))
			return false;

		if (exists(username))
			return false;

		User user = new User(username, passwordEncoder.encode(password), firstName, lastName, email, city, country);
		try {
			userRepository.save(user);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}