package com.jovo.ScienceCenter.dto;


public class FormFieldDTO {
    private String name;
    private String type;
    private boolean optional;

    public FormFieldDTO() {

    }

    public FormFieldDTO(String name, String type, boolean optional) {
        this.name = name;
        this.type = type;
        this.optional = optional;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean getOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}
