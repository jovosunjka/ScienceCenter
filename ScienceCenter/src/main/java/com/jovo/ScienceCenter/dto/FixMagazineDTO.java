package com.jovo.ScienceCenter.dto;

public class FixMagazineDTO {
    private String processInstanceId;
    private String magazineName;

    public FixMagazineDTO() {

    }
    public FixMagazineDTO(String processInstanceId, String magazineName) {
        this.processInstanceId = processInstanceId;
        this.magazineName = magazineName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }
}
