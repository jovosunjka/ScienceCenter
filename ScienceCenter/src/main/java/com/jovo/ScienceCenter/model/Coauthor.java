package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "coauthor")
public class Coauthor implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", unique = false, nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = false, nullable = false)
    private String lastName;

    @Column(name = "email", unique = false, nullable = false)
    private String email;

    @Column(name = "address", unique = false, nullable = false)
    private String address;

    @ManyToOne
    private City city;

    @Column(name = "country", unique = false, nullable = false)
    private String country;

    @Column(name = "registered_user_data_id", unique = true, nullable = true)
    private Long registeredUserDataId;


    public Coauthor() {

    }

    public Coauthor(String firstName, String lastName, String email, String address, City city, String country,
                  Long registeredUserDataId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.registeredUserDataId = registeredUserDataId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getRegisteredUserDataId() {
        return registeredUserDataId;
    }

    public void setRegisteredUserDataId(Long registeredUserDataId) {
        this.registeredUserDataId = registeredUserDataId;
    }
}
