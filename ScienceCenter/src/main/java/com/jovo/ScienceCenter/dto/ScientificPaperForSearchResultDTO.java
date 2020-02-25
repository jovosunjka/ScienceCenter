package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScientificPaperForSearchResultDTO {
    private long id;
    private String magazineName;
    private String scientificPaperAbstract;
    private String scientificArea;
    private String author;
    private String coauthors;
    private String paidUpTo;
    private List<DTO> plans;


    public ScientificPaperForSearchResultDTO() {

    }

    public ScientificPaperForSearchResultDTO(long id, String magazineName, String scientificPaperAbstract,
                                             String scientificArea, String author, String coauthors, String paidUpTo,
                                             List<Plan> plans) {
        this.id = id;
        this.magazineName = magazineName;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.scientificArea = scientificArea;
        this.author = author;
        this.coauthors = coauthors;
        this.paidUpTo = paidUpTo;
        if (plans == null)  {
            this.plans = new ArrayList<DTO>();
        }
        else {
            this.plans = plans.stream()
                    .map(plan -> new DTO(plan.getId(), plan.getIntervalUnit() + ", "
                            + plan.getIntervalCount() + ", " + plan.getPrice()))
                    .collect(Collectors.toList());
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
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

    public String getPaidUpTo() {
        return paidUpTo;
    }

    public void setPaidUpTo(String paidUpTo) {
        this.paidUpTo = paidUpTo;
    }

    public List<DTO> getPlans() {
        return plans;
    }

    public void setPlans(List<DTO> plans) {
        this.plans = plans;
    }
}
