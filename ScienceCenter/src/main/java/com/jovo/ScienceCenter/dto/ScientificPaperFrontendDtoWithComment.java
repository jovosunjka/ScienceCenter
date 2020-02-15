package com.jovo.ScienceCenter.dto;

public class ScientificPaperFrontendDtoWithComment extends ScientificPaperFrontendDTO {
    protected String commentForAuthor;


    public ScientificPaperFrontendDtoWithComment() {

    }

    public ScientificPaperFrontendDtoWithComment(String taskId, String title, String keywords, String scientificPaperAbstract,
                                                 String scientificArea, String author, String coauthors, String commentForAuthor) {
        super(taskId, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors);
        this.commentForAuthor = commentForAuthor;
    }

    public String getCommentForAuthor() {
        return commentForAuthor;
    }

    public void setCommentForAuthor(String commentForAuthor) {
        this.commentForAuthor = commentForAuthor;
    }
}
