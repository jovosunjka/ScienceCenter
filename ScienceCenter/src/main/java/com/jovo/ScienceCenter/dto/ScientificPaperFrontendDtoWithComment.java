package com.jovo.ScienceCenter.dto;

public class ScientificPaperFrontendDtoWithComment extends ScientificPaperFrontendDTO {
<<<<<<< HEAD
    protected String commentForAuthor;
=======
    private String comment;
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f


    public ScientificPaperFrontendDtoWithComment() {

    }

    public ScientificPaperFrontendDtoWithComment(String taskId, String title, String keywords, String scientificPaperAbstract,
<<<<<<< HEAD
                                                 String scientificArea, String author, String coauthors, String commentForAuthor) {
        super(taskId, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors);
        this.commentForAuthor = commentForAuthor;
    }

    public String getCommentForAuthor() {
        return commentForAuthor;
    }

    public void setCommentForAuthor(String commentForAuthor) {
        this.commentForAuthor = commentForAuthor;
=======
                                                 String scientificArea, String author, String coauthors, String comment) {
        super(taskId, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f
    }
}
