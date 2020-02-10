package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_data")
public class UserData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "camunda_user_id", unique = true, nullable = false)
    private String camundaUserId;

    @Column(name = "user_status", unique = false, nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private Status userStatus;

    @Column(name = "city", unique = false, nullable = false)
    private String city;

    @Column(name = "country", unique = false, nullable = false)
    private String country;

    @Column(name = "reviewer", unique = false, nullable = false)
    private boolean reviewer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", nullable = false, updatable = false) })
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_scientific_areas", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "scientific_area_id", nullable = false, updatable = false) })
    private Set<ScientificArea> scientificAreas;


    public UserData() {

    }

    public UserData(String camundaUserId, String city, String country, List<ScientificArea> scientificAreas, Role role) {
        this.camundaUserId = camundaUserId;
        this.userStatus = Status.PENDING;
        this.city = city;
        this.country = country;
        this.reviewer = false;
        this.roles = new HashSet<Role>();
        this.roles.add(role);
        this.scientificAreas = new HashSet<ScientificArea>(scientificAreas);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCamundaUserId() {
        return camundaUserId;
    }

    public void setCamundaUserId(String camundaUserId) {
        this.camundaUserId = camundaUserId;
    }

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() { return country; }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isReviewer() { return reviewer; }

    public void setReviewer(boolean reviewer) { this.reviewer = reviewer; }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<ScientificArea> getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(Set<ScientificArea> scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return reviewer == userData.reviewer &&
                id.equals(userData.id) &&
                camundaUserId.equals(userData.camundaUserId) &&
                userStatus == userData.userStatus &&
                city.equals(userData.city) &&
                country.equals(userData.country) &&
                roles.equals(userData.roles) &&
                scientificAreas.equals(userData.scientificAreas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, camundaUserId, userStatus, city, country, reviewer, roles, scientificAreas);
    }
}
