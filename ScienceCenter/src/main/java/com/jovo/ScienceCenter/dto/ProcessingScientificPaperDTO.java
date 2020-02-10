package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.StatusOfScientificPaper;

public class ProcessingScientificPaperDTO {
    private StatusOfScientificPaper status;
    private String comment;

    public ProcessingScientificPaperDTO() {

    }

    public ProcessingScientificPaperDTO(StatusOfScientificPaper status, String comment) {
        this.status = status;
        this.comment = comment;
    }

    public StatusOfScientificPaper getStatus() {
        return status;
    }

    public void setStatus(StatusOfScientificPaper status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
