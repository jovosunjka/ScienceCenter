package com.jovo.ScienceCenter.dto;

import java.util.List;

public class FormFieldsForPaymentTypesDTO {
    private List<FormFieldsForPaymentTypeDTO> types;

    public FormFieldsForPaymentTypesDTO() {

    }

    public FormFieldsForPaymentTypesDTO(List<FormFieldsForPaymentTypeDTO> types) {
        this.types = types;
    }

    public List<FormFieldsForPaymentTypeDTO> getTypes() {
        return types;
    }

    public void setTypes(List<FormFieldsForPaymentTypeDTO> types) {
        this.types = types;
    }
}
