package com.jovo.ScienceCenter.dto;

public class ScientificPaperFrontendDTOWithId {
    private long id;
    private String title;
    private String keywords;
    private String scientificPaperAbstract;
    private String scientificArea;
    private String author;
    private String coauthors;

    public ScientificPaperFrontendDTOWithId() {

    }

    public ScientificPaperFrontendDTOWithId(long id, String title, String keywords, String scientificPaperAbstract,
                                            String scientificArea, String author, String coauthors) {
        this.id = id;
        this.title = title;
        this.keywords = keywords;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.scientificArea = scientificArea;
        this.author = author;
        this.coauthors = coauthors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setScientificPaperAbstract(String scientificPaperAbstract) {
        this.scientificPaperAbstract = scientificPaperAbstract;
    }

    public String getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(String scientificArea) {
        this.scientificArea = scientificArea;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(String coauthors) {
        this.coauthors = coauthors;
    }
}
