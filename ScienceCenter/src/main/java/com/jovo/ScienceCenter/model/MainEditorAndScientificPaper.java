package com.jovo.ScienceCenter.model;

import java.io.Serializable;

public class MainEditorAndScientificPaper implements Serializable {
    private UserData mainEditor;
    private ScientificPaper scientificPaper;


    public MainEditorAndScientificPaper() {

    }

    public MainEditorAndScientificPaper(UserData mainEditor, ScientificPaper scientificPaper) {
        this.mainEditor = mainEditor;
        this.scientificPaper = scientificPaper;
    }

    public UserData getMainEditor() {
        return mainEditor;
    }

    public void setMainEditor(UserData mainEditor) {
        this.mainEditor = mainEditor;
    }

    public ScientificPaper getScientificPaper() {
        return scientificPaper;
    }

    public void setScientificPaper(ScientificPaper scientificPaper) {
        this.scientificPaper = scientificPaper;
    }
}
