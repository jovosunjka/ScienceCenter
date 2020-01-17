package com.jovo.ScienceCenter.dto;

public class AddEditorsAndReviewerDTO {
    private String editors;
    private String reviewers;

    public AddEditorsAndReviewerDTO() {

    }

    public AddEditorsAndReviewerDTO(String editors, String reviewers) {
        this.editors = editors;
        this.reviewers = reviewers;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getReviewers() {
        return reviewers;
    }

    public void setReviewers(String reviewers) {
        this.reviewers = reviewers;
    }
}
