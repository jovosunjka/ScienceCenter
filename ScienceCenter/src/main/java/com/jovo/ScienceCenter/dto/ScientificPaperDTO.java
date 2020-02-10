package com.jovo.ScienceCenter.dto;

import java.util.List;

public class ScientificPaperDTO {
    private String title;
    private String keywords;
    private String scientificPaperAbstract;
    private String selectedScientificAreaId;
    private String coauthors;


    public ScientificPaperDTO() {

    }

    public ScientificPaperDTO(String title, String keywords, String scientificPaperAbstract,
                              String selectedScientificAreaId, String coauthors) {
        this.title = title;
        this.keywords = keywords;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.selectedScientificAreaId = selectedScientificAreaId;
        this.coauthors = coauthors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getScientificPaperAbstract() {
        return scientificPaperAbstract;
    }

    public void setScientificPaperAbstract(String scientificPaperAbstract) { this.scientificPaperAbstract = scientificPaperAbstract; }

    public String getScientificAreaId() {
        return selectedScientificAreaId;
    }

    public void setselectedScientificAreaId(String selectedScientificAreaId) { this.selectedScientificAreaId = selectedScientificAreaId; }

    public String getCoauthors() { return coauthors; }

    public void setCoauthors(String coauthors) {
        this.coauthors = coauthors;
    }
}
