package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "magazine")
public class Magazine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "issn", unique = true, nullable = false)
    private String issn;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = false, nullable = false)
    private String password;
    // mora biti enkriptovan algoritmom, kojim ce moci i da se dekriptuje, jer se salje PaymentConcentrator-u
    //algoritam mora biti dvosmeran

    @Column(name = "merchant_id", unique = true, nullable = true)
    private String merchantId; //jwt token

    @Column(name = "membership_fee", unique = false, nullable = false)
    private double membershipFee;

    @Column(name = "currency", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "magazine_scientific_areas", joinColumns = {
            @JoinColumn(name = "magazine_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "scientific_area_id", nullable = false, updatable = false) })
    private Set<ScientificArea> scientificAreas;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserData mainEditor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "magazine_editors",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "editor_id", referencedColumnName = "id"))
    private Set<UserData> editors = new HashSet<>(); // jedan editor ne moze biti u vise magazina (proveriti ovu tvrdnju)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "magazine_reviewers",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reviewer_id", referencedColumnName = "id"))
    private Set<UserData> reviewers = new HashSet<UserData>();

    @Column(name = "payer_type", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PayerType payerType;

<<<<<<< HEAD
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "magazine_scientific_papers",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scientific_paper_id", referencedColumnName = "id"))
    private Set<ScientificPaper> scientificPapers = new HashSet<ScientificPaper>();
=======
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "magazine_scientific_papers",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scientific_paper_id", referencedColumnName = "id"))
    private List<ScientificPaper> scientificPapers = new ArrayList<ScientificPaper>();
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f

    @Column(name = "magazine_status", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status magazineStatus;

    public Magazine() {

    }

    public Magazine(String name, String issn, String username, String password, double membershipFee,
                    Currency currency, List<ScientificArea> scientificAreas, UserData mainEditor, PayerType payerType) {
        this.name = name;
        this.issn = issn;
        this.username = username;
        this.password = password;
        this.merchantId = null;
        this.membershipFee = membershipFee;
        this.currency = currency;
        this.scientificAreas = new HashSet<ScientificArea>(scientificAreas);
        this.mainEditor = mainEditor;
        this.payerType = payerType;
        this.magazineStatus = Status.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() { return issn; }

    public void setIssn(String issn) { this.issn = issn; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public Set<ScientificArea> getScientificAreas() { return scientificAreas; }

    public void setScientificAreas(Set<ScientificArea> scientificAreas) { this.scientificAreas = scientificAreas; }

    public UserData getMainEditor() { return mainEditor; }

    public void setMainEditor(UserData mainEditor) { this.mainEditor = mainEditor; }

    public Set<UserData> getEditors() { return editors; }

    public void setEditors(Set<UserData> editors) { this.editors = editors; }

    public Set<UserData> getReviewers() { return reviewers; }

    public void setReviewers(Set<UserData> reviewers) { this.reviewers = reviewers; }

    public PayerType getPayerType() { return payerType; }

    public void setPayerType(PayerType payerType) { this.payerType = payerType; }

<<<<<<< HEAD
    public Set<ScientificPaper> getScientificPapers() { return scientificPapers; }

    public void setScientificPapers(Set<ScientificPaper> scientificPapers) { this.scientificPapers = scientificPapers; }
=======
    public List<ScientificPaper> getScientificPapers() { return scientificPapers; }

    public void setScientificPapers(List<ScientificPaper> scientificPapers) { this.scientificPapers = scientificPapers; }
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f

    public Status getMagazineStatus() { return magazineStatus; }

    public void setMagazineStatus(Status magazineStatus) { this.magazineStatus = magazineStatus; }
}
