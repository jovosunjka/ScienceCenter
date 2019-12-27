package com.jovo.ScienceCenter.dto;

import javax.persistence.*;
import java.util.List;

public class PaymentAccountDTO {
    private String name; // name of magazine
    private List<PaymentDTO> payments;


    public PaymentAccountDTO() {

    }

    public PaymentAccountDTO(String name, List<PaymentDTO> payments) {
        this.name = name;
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }
}
