package com.jovo.ScienceCenter.dto;

public class ScienceCenterDTO {
    private String name;
    private String baseUrl;

    public ScienceCenterDTO() {

    }

    public ScienceCenterDTO(String name, String baseUrl) {
        this.name = name;
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
