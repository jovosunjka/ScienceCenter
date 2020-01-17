package com.jovo.ScienceCenter.dto;

public class PropertyDTO {
    private String identifier; // account number, paypal account, bitcoin account, ...
    private String value;

    public PropertyDTO() {

    }

    public PropertyDTO(String identifier, String value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
