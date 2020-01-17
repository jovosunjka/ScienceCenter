package com.jovo.ScienceCenter.dto;

public class ConfirmRegistrationDTO {
    private String token;

    public ConfirmRegistrationDTO() {

    }

    public ConfirmRegistrationDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
