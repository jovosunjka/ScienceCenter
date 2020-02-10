package com.jovo.ScienceCenter.dto;

public class TaskIdAndTitleDTO {
    private String taskId;
    private String title;

    public TaskIdAndTitleDTO() {

    }

    public TaskIdAndTitleDTO(String taskId, String title) {
        this.taskId = taskId;
        this.title = title;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
