package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "scientific_paper")
public class ScientificPaper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "keywords", unique = false, nullable = false)
    private String keywords;

    @Column(name = "scientific_paper_abstract", unique = false, nullable = false)
    private String scientificPaperAbstract;

    @Column(name = "relative_path_to_file", unique = false, nullable = false)
    private String relativePathToFile;

    @ManyToOne
    private ScientificArea scientificArea;

    @ManyToOne
    private UserData author;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Coauthor> coauthors;


    public ScientificPaper() {

    }

    public ScientificPaper(String title, String keywords, String scientificPaperAbstract,
                           String relativePathToFile, ScientificArea scientificArea, UserData author,
                           List<Coauthor> coauthors) {
        this.title = title;
        this.keywords = keywords;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.relativePathToFile = relativePathToFile;
        this.scientificArea = scientificArea;
        this.author = author;
        this.coauthors = coauthors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setScientificPaperAbstract(String scientificPaperAbstract) { this.scientificPaperAbstract = scientificPaperAbstract; }

    public String getRelativePathToFile() { return relativePathToFile; }

    public void setRelativePathToFile(String relativePathToFile) { this.relativePathToFile = relativePathToFile; }

    public ScientificArea getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(ScientificArea scientificArea) {
        this.scientificArea = scientificArea;
    }

    public UserData getAuthor() {
        return author;
    }

    public void setAuthor(UserData author) {
        this.author = author;
    }

    public List<Coauthor> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<Coauthor> coauthors) {
        this.coauthors = coauthors;
    }
}
