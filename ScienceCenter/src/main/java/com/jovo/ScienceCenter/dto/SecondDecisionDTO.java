package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.EditorDecision;

public class SecondDecisionDTO {
    private EditorDecision editorDecision;
    private String editorCommentForAuthor;


    public SecondDecisionDTO() {

    }

    public SecondDecisionDTO(EditorDecision editorDecision, String editorCommentForAuthor) {
        this.editorDecision = editorDecision;
        this.editorCommentForAuthor = editorCommentForAuthor;
    }

    public EditorDecision getEditorDecision() {
        return editorDecision;
    }

    public void setEditorDecision(EditorDecision editorDecision) {
        this.editorDecision = editorDecision;
    }

    public String getEditorCommentForAuthor() {
        return editorCommentForAuthor;
    }

    public void setEditorCommentForAuthor(String editorCommentForAuthor) {
        this.editorCommentForAuthor = editorCommentForAuthor;
    }
}
