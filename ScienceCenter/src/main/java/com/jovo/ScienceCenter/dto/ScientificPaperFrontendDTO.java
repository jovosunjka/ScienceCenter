package com.jovo.ScienceCenter.dto;

<<<<<<< HEAD
=======
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import org.camunda.bpm.engine.identity.User;
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f

public class ScientificPaperFrontendDTO {
    protected String taskId;
    protected String title;
    protected String keywords;
    protected String scientificPaperAbstract;
    protected String scientificArea;
    protected String author;
    protected String coauthors;


    public ScientificPaperFrontendDTO() {

    }

    public ScientificPaperFrontendDTO(String taskId, String title, String keywords, String scientificPaperAbstract,
                                      String scientificArea, String author, String coauthors) {
        this.taskId = taskId;
        this.title = title;
        this.keywords = keywords;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.scientificArea = scientificArea;
        this.author = author;
        this.coauthors = coauthors;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
