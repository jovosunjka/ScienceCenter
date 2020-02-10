package com.jovo.ScienceCenter.util;

import com.jovo.ScienceCenter.model.StatusOfScientificPaperAfterReviewing;

import java.io.Serializable;

public class ReviewingResult implements Serializable {
    private String reviewer;
    private StatusOfScientificPaperAfterReviewing statusAfterReviewing;
    private String commentForAuthor;
    private String commentForEditor;

    public ReviewingResult() {

    }

    public ReviewingResult(String reviewer, StatusOfScientificPaperAfterReviewing statusAfterReviewing,
                           String commentForAuthor, String commentForEditor) {
        this.reviewer = reviewer;
        this.statusAfterReviewing = statusAfterReviewing;
        this.commentForAuthor = commentForAuthor;
        this.commentForEditor = commentForEditor;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public StatusOfScientificPaperAfterReviewing getStatusAfterReviewing() {
        return statusAfterReviewing;
    }

    public void setStatusAfterReviewing(StatusOfScientificPaperAfterReviewing statusAfterReviewing) {
        this.statusAfterReviewing = statusAfterReviewing;
    }

    public String getCommentForAuthor() {
        return commentForAuthor;
    }

    public void setCommentForAuthor(String commentForAuthor) {
        this.commentForAuthor = commentForAuthor;
    }

    public String getCommentForEditor() {
        return commentForEditor;
    }

    public void setCommentForEditor(String commentForEditor) {
        this.commentForEditor = commentForEditor;
    }
}
