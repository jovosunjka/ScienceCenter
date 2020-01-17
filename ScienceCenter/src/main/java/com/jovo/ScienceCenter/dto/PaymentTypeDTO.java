package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Currency;
import java.util.List;


public class PaymentTypeDTO {
    private String type; // card payment, paypal, bitcoin, ...
    private List<PropertyDTO> properties;
    private Currency currency;

    public PaymentTypeDTO() {

    }

    public PaymentTypeDTO(String type, List<PropertyDTO> properties, Currency currency) {
        this.type = type;
        this.properties = properties;
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertyDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDTO> properties) {
        this.properties = properties;
    }

    public Currency getCurrency() { return currency; }

    public void setCurrency(Currency currency) { this.currency = currency; }
}
