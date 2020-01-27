package com.jovo.ScienceCenter.dto;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public class FormFieldsForPaymentTypeDTO {
    private String paymentType;
    private List<FormFieldDTO> formFields;


    public FormFieldsForPaymentTypeDTO() {

    }

    public FormFieldsForPaymentTypeDTO(String paymentType, List<FormFieldDTO> formFields) {
        this.paymentType = paymentType;
        this.formFields = formFields;
    }


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<FormFieldDTO> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldDTO> formFields) {
        this.formFields = formFields;
    }
}
