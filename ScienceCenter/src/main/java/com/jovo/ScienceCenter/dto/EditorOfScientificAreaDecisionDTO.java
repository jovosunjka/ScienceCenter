package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.StatusOfScientificPaperAfterReviewing;

public class EditorOfScientificAreaDecisionDTO {
    private StatusOfScientificPaperAfterReviewing editorOfScientificAreaDecision;


    public EditorOfScientificAreaDecisionDTO() {

    }

    public EditorOfScientificAreaDecisionDTO(StatusOfScientificPaperAfterReviewing editorOfScientificAreaDecision) {
        this.editorOfScientificAreaDecision = editorOfScientificAreaDecision;
    }


    public StatusOfScientificPaperAfterReviewing getEditorOfScientificAreaDecision() {
        return editorOfScientificAreaDecision;
    }

    public void setEditorOfScientificAreaDecision(StatusOfScientificPaperAfterReviewing editorOfScientificAreaDecision) {
        this.editorOfScientificAreaDecision = editorOfScientificAreaDecision;
    }
}
