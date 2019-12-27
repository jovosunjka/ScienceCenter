package com.jovo.ScienceCenter.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PaymentDTO {
    private String type; // card payment, paypal, bitcoin, ...
    private String identifier; // card number, paypal identifier, bitcoin identifier, ...

    public PaymentDTO() {

    }

    public PaymentDTO(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
