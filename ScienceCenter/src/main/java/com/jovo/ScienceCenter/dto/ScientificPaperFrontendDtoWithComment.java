package com.jovo.ScienceCenter.dto;

public class ScientificPaperFrontendDtoWithComment extends ScientificPaperFrontendDTO {
    private String comment;


    public ScientificPaperFrontendDtoWithComment() {

    }

    public ScientificPaperFrontendDtoWithComment(String taskId, String title, String keywords, String scientificPaperAbstract,
                                                 String scientificArea, String author, String coauthors, String comment) {
        super(taskId, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
