package com.jovo.ScienceCenter.dto;

public class AddPaymentTypesDTO {
    private String paymentTypes;

    public AddPaymentTypesDTO() {

    }

    public AddPaymentTypesDTO(String paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public String getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(String paymentTypes) {
        this.paymentTypes = paymentTypes;
    }
}
