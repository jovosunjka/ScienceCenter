package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.StatusOfScientificPaperAfterReviewing;
import com.jovo.ScienceCenter.util.ReviewingResult;

public class ReviewingResultDTO {
    private StatusOfScientificPaperAfterReviewing statusAfterReviewing;
    private String commentForAuthor;

    public ReviewingResultDTO() {

    }

    public ReviewingResultDTO(ReviewingResult reviewingResult) {
        this(reviewingResult.getStatusAfterReviewing(), reviewingResult.getCommentForAuthor());
    }

    public ReviewingResultDTO(StatusOfScientificPaperAfterReviewing statusAfterReviewing, String commentForAuthor) {
        this.statusAfterReviewing = statusAfterReviewing;
        this.commentForAuthor = commentForAuthor;
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
}
