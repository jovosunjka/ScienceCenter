package com.jovo.ScienceCenter.dto;

public class ScientificPaperFrontendDTOWithMagazineName extends ScientificPaperFrontendDTOWithId {
    private String magazineName;

    public ScientificPaperFrontendDTOWithMagazineName() {

    }

    public ScientificPaperFrontendDTOWithMagazineName(long id, String title, String keywords, String scientificPaperAbstract,
                                                      String scientificArea, String author, String coauthors, String magazineName) {
        super(id, title, keywords, scientificPaperAbstract, scientificArea, author, coauthors);
        this.magazineName = magazineName;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }
}
