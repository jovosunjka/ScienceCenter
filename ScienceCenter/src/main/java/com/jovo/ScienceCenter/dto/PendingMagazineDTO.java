package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Magazine;

public class PendingMagazineDTO extends MagazineDTO {
    private String taskId;

    public PendingMagazineDTO(Magazine magazine) {
        super(magazine, "Unknowon");
        this.taskId = null;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
