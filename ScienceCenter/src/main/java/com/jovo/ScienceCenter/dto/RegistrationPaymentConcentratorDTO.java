package com.jovo.ScienceCenter.dto;


import java.util.List;

public class RegistrationPaymentConcentratorDTO {
    private String name; // name of magazine
    private String username;
    private String password;
    private String repeatedPassword;
    private List<PaymentTypeDTO> payments;


    public RegistrationPaymentConcentratorDTO() {

    }

    public RegistrationPaymentConcentratorDTO(String name, String username, String password, String repeatedPassword,
                                              List<PaymentTypeDTO> payments) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<PaymentTypeDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentTypeDTO> payments) {
        this.payments = payments;
    }
}
