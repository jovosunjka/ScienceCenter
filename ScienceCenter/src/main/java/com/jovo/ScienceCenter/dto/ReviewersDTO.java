package com.jovo.ScienceCenter.dto;

import java.util.List;

public class ReviewersDTO {
    private List<Long> reviewers;

    public ReviewersDTO() {

    }

    public ReviewersDTO(List<Long> reviewers) {
        this.reviewers = reviewers;
    }

    public List<Long> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Long> reviewers) {
        this.reviewers = reviewers;
    }
}
