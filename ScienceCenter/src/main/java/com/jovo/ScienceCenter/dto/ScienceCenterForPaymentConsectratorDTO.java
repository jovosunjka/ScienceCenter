package com.jovo.ScienceCenter.dto;

import java.util.List;

public class ScienceCenterForPaymentConsectratorDTO {
    // private String name;
    private List<RegistrationPaymentConcentratorDTO> paymentAccounts;

    public ScienceCenterForPaymentConsectratorDTO() {

    }

    public ScienceCenterForPaymentConsectratorDTO(/*String name,*/ List<RegistrationPaymentConcentratorDTO> paymentAccounts) {
        //this.name = name;
        this.paymentAccounts = paymentAccounts;
    }

    /*public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }*/

    public List<RegistrationPaymentConcentratorDTO> getPaymentAccounts() {
        return paymentAccounts;
    }

    public void setPaymentAccounts(List<RegistrationPaymentConcentratorDTO> paymentAccounts) {
        this.paymentAccounts = paymentAccounts;
    }
}
