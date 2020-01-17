package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MagazineDTO {
    private long id;
    private String name;
    private String issn;
    private double membershipFee;
    private Currency currency;
    private String scientificAreas;
    private String mainEditor;
    private String editors;
    private String reviewers;

    public MagazineDTO() {

    }

    public MagazineDTO(long id, String name, String issn, double membershipFee, Currency currency, String scientificAreas,
                       String mainEditor, String editors, String reviewers) {
        this.id = id;
        this.name = name;
        this.issn = issn;
        this.membershipFee = membershipFee;
        this.currency = currency;
        this.scientificAreas = scientificAreas;
        this.mainEditor = mainEditor;
        this.editors = editors;
        this.reviewers = reviewers;
    }

    public MagazineDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.name = magazine.getName();
        this.issn = magazine.getIssn();
        this.membershipFee = magazine.getMembershipFee();
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

    public double getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(double membershipFee) {
        this.membershipFee = membershipFee;
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
