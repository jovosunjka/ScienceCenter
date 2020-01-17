package com.jovo.ScienceCenter.dto;


import java.util.List;

public class RegistrationUserDTO {
	private String username;
	private String password;
	private String repeatedPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String country;
	private List<Long> scientificAreas;
	private boolean reviewer;
	
	public RegistrationUserDTO() {
		
	}


	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Long> getScientificAreas() { return scientificAreas; }

	public void setScientificAreas(List<Long> scientificAreas) { this.scientificAreas = scientificAreas; }

	public boolean isReviewer() { return reviewer; }

	public void setReviewer(boolean reviewer) { this.reviewer = reviewer; }
}

