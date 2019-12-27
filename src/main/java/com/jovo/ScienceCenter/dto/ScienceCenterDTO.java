package com.jovo.ScienceCenter.dto;

import java.util.List;

public class ScienceCenterDTO {
    // private String name;
    private List<PaymentAccountDTO> paymentAccounts;

    public ScienceCenterDTO() {

    }

    public ScienceCenterDTO(/*String name,*/ List<PaymentAccountDTO> paymentAccounts) {
        //this.name = name;
        this.paymentAccounts = paymentAccounts;
    }

    /*public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }*/

    public List<PaymentAccountDTO> getPaymentAccounts() {
        return paymentAccounts;
    }

    public void setPaymentAccounts(List<PaymentAccountDTO> paymentAccounts) {
        this.paymentAccounts = paymentAccounts;
    }
}
