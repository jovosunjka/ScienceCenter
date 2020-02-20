package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.ScientificArea;
import com.jovo.ScienceCenter.model.UserData;

import java.util.List;
import java.util.stream.Collectors;


public class MagazineWithoutPaidStatusDTO {
    protected long id;
    protected String name;
    protected String issn;
    protected Currency currency;
    protected String scientificAreas;
    protected String mainEditor;
    protected String editors;
    protected String reviewers;

    public MagazineWithoutPaidStatusDTO() {

    }

    public MagazineWithoutPaidStatusDTO(long id, String name, String issn, Currency currency,
                                        String scientificAreas, String mainEditor, String editors, String reviewers) {
        this.id = id;
        this.name = name;
        this.issn = issn;
        this.currency = currency;
        this.scientificAreas = scientificAreas;
        this.mainEditor = mainEditor;
        this.editors = editors;
        this.reviewers = reviewers;
    }

    public MagazineWithoutPaidStatusDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.name = magazine.getName();
        this.issn = magazine.getIssn();
        this.currency = magazine.getCurrency();
        List<String> scientificAreaNames = magazine.getScientificAreas().stream()
                .map(ScientificArea::getName).collect(Collectors.toList());
        String scientificAreaNamesStr = String.join(", ", scientificAreaNames);
        this.scientificAreas = scientificAreaNamesStr;
        this.mainEditor = magazine.getMainEditor().getCamundaUserId();
        List<String> editorUsernames = magazine.getEditors().stream()
                .map(UserData::getCamundaUserId).collect(Collectors.toList());
        String editorsStr = String.join(", ", editorUsernames);
        this.editors = editorsStr;
        List<String> reviewerUsernames = magazine.getReviewers().stream()
                .map(UserData::getCamundaUserId).collect(Collectors.toList());
        String reviewersStr = String.join(", ", reviewerUsernames);
        this.reviewers = reviewersStr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public String getMainEditor() {
        return mainEditor;
    }

    public void setMainEditor(String mainEditor) {
        this.mainEditor = mainEditor;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getReviewers() {
        return reviewers;
    }

    public void setReviewers(String reviewers) {
        this.reviewers = reviewers;
    }
    
    
}
