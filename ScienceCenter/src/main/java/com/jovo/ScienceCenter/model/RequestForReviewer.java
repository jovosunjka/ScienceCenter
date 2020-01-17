package com.jovo.ScienceCenter.model;

import javax.persistence.*;

@Entity
@Table(name = "request_for_reviewer")
public class RequestForReviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private UserData userData;


    public RequestForReviewer() {

    }

    public RequestForReviewer(UserData userData) {
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
