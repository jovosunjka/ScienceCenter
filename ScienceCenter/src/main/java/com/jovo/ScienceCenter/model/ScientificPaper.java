package com.jovo.ScienceCenter.model;

import javax.persistence.*;

import com.jovo.ScienceCenter.exception.NotFoundException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "abstract", unique = false, nullable = false)
    private String scientificPaperAbstract;

    @Column(name = "relative_path_to_file", unique = false, nullable = false)
    private String relativePathToFile;

    @ManyToOne
    private ScientificArea scientificArea;

    @ManyToOne
    private UserData author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "scientific_paper_coauthors", joinColumns = {
            @JoinColumn(name = "scientific_paper_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "plan_id", nullable = false, updatable = false) })
    private Set<Coauthor> coauthors;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "scientific_paper_plans", joinColumns = {
            @JoinColumn(name = "scientific_paper_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "plan_id", nullable = false, updatable = false) })
    //@ManyToMany(fetch = FetchType.LAZY)
    private List<Plan> plans;
    
    @Column(name = "magazine_name", unique = false, nullable = false)
    private String magazineName;

    @Column(name = "status", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status scientificPaperStatus;


    public ScientificPaper() {

    }

    public ScientificPaper(String title, String keywords, String scientificPaperAbstract,
            String relativePathToFile, ScientificArea scientificArea, UserData author,
            List<Coauthor> coauthors, String magazineName) {
		this.title = title;
		this.keywords = keywords;
		this.scientificPaperAbstract = scientificPaperAbstract;
		this.relativePathToFile = relativePathToFile;
		this.scientificArea = scientificArea;
		this.author = author;
		this.coauthors = new HashSet<Coauthor>(coauthors);
		this.magazineName = magazineName;
		this.scientificPaperStatus = Status.PENDING;
	}
    
    public ScientificPaper(String title, String keywords, String scientificPaperAbstract,
                           String relativePathToFile, ScientificArea scientificArea, UserData author,
                           List<Coauthor> coauthors, String magazineName, List<Plan> plans) {
        this.title = title;
        this.keywords = keywords;
        this.scientificPaperAbstract = scientificPaperAbstract;
        this.relativePathToFile = relativePathToFile;
        this.scientificArea = scientificArea;
        this.author = author;
        this.coauthors = new HashSet<Coauthor>(coauthors);
        this.magazineName = magazineName;
        this.scientificPaperStatus = Status.PENDING;
        this.plans = plans;
    }
    
    
    public Plan getPlan(Long id) {
    	return plans.stream()
    		.filter(p -> p.getId().longValue() == id.longValue())
    		.findFirst().orElseThrow(() -> new NotFoundException("Plan (id=" + id + ") not found!"));
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

    public Set<Coauthor> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(Set<Coauthor> coauthors) {
        this.coauthors = coauthors;
    }

    public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public String getMagazineName() { return magazineName; }

    public void setMagazineName(String magazineName) { this.magazineName = magazineName; }

    public Status getScientificPaperStatus() { return scientificPaperStatus; }

    public void setScientificPaperStatus(Status scientificPaperStatus) { this.scientificPaperStatus = scientificPaperStatus; }
}
