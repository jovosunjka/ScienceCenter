package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.EditorDecision;

public class FinalDecisionDTO {
    private EditorDecision editorDecision;
    private String editorCommentForAuthor;


    public FinalDecisionDTO() {

    }

    public FinalDecisionDTO(EditorDecision editorDecision) {
        this.editorDecision = editorDecision;
    }

    public EditorDecision getEditorDecision() {
        return editorDecision;
    }

    public void setEditorDecision(EditorDecision editorDecision) {
        this.editorDecision = editorDecision;
    }

}
