package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Status;

import java.util.List;

public class UserDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String scientificAreas;
    private boolean reviewer;
    private Status userStatus;
    private List<DTO> roles;


    public UserDTO() {

    }

    public UserDTO(long id, String username, String firstName, String lastName, String email, String city,
                   String country, String scientificAreas, boolean reviewer, Status userStatus, List<DTO> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.country = country;
        this.scientificAreas = scientificAreas;
        this.reviewer = reviewer;
        this.userStatus = userStatus;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public boolean isReviewer() {
        return reviewer;
    }

    public void setReviewer(boolean reviewer) {
        this.reviewer = reviewer;
    }

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

    public List<DTO> getRoles() {
        return roles;
    }

    public void setRoles(List<DTO> roles) {
        this.roles = roles;
    }
}
