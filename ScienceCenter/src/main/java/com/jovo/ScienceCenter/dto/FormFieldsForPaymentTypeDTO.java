package com.jovo.ScienceCenter.dto;


public class FormFieldsForPaymentTypeDTO {
    private String paymentType;
    private String formFields;


    public FormFieldsForPaymentTypeDTO() {

    }

    public FormFieldsForPaymentTypeDTO(String paymentType, String formFields) {
        this.paymentType = paymentType;
        this.formFields = formFields;
    }


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getFormFields() {
        return formFields;
    }

    public void setFormFields(String formFields) {
        this.formFields = formFields;
    }
}
