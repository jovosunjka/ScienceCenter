package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.StatusOfScientificPaperAfterReviewing;

public class ReviewingScientificPaperDTO {
    private StatusOfScientificPaperAfterReviewing statusAfterReviewing;
    private String commentForAuthor;
    private String commentForEditor;

    public ReviewingScientificPaperDTO() {

    }

    public ReviewingScientificPaperDTO(StatusOfScientificPaperAfterReviewing statusAfterReviewing, String commentForAuthor,
                                       String commentForEditor) {
        this.statusAfterReviewing = statusAfterReviewing;
        this.commentForAuthor = commentForAuthor;
        this.commentForEditor = commentForEditor;
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
