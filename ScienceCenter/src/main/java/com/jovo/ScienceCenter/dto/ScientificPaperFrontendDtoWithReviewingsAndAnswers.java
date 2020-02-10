package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.util.ReviewingResult;

import java.util.List;

public class ScientificPaperFrontendDtoWithReviewingsAndAnswers extends ScientificPaperFrontendDtoWithReviewings {
    private String answers;


    public ScientificPaperFrontendDtoWithReviewingsAndAnswers() {

    }

    public ScientificPaperFrontendDtoWithReviewingsAndAnswers(String taskId, String title, String keywords,
                                                              String scientificPaperAbstract, String scientificArea,
                                                              String author, String coauthors,
                                                              List<ReviewingResult> reviewings, String answers) {
        super(taskId, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors, reviewings);
        this.answers = answers;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
