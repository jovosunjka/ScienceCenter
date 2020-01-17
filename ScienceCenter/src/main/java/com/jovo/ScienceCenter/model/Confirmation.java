package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "confirmation")
public class Confirmation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    //@Column(name = "expiration_date", unique = false, nullable = false)
    //private LocalDateTime expirationDate;

    @OneToOne(fetch = FetchType.EAGER)
    private UserData userData;


    public Confirmation() {

    }

    public Confirmation(String token, UserData userData) {
        this.token = token;
        //this.expirationDate = LocalDateTime.now().plusHours(1);
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*public LocalDateTime getExpirationDate() {
        return expirationDate;
    }*/

    /*public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }*/

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
