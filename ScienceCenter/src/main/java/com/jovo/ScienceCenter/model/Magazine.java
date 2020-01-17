package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "magazine")
public class Magazine {
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "magazine_editors",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "editor_id", referencedColumnName = "id"))
    private List<UserData> editors = new ArrayList<UserData>(); // jedan editor ne moze biti u vise magazina (proveriti ovu tvrdnju)

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "magazine_reviewers",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reviewer_id", referencedColumnName = "id"))
    private List<UserData> reviewers = new ArrayList<UserData>();

    @Column(name = "magazine_status", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status magazineStatus;

    public Magazine() {

    }

    public Magazine(String name, String issn, String username, String password, double membershipFee,
                    Currency currency, List<ScientificArea> scientificAreas, UserData mainEditor) {
        this.name = name;
        this.issn = issn;
        this.username = username;
        this.password = password;
        this.merchantId = null;
        this.membershipFee = membershipFee;
        this.currency = currency;
        this.scientificAreas = new HashSet<ScientificArea>(scientificAreas);
        this.mainEditor = mainEditor;
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

    public List<UserData> getEditors() { return editors; }

    public void setEditors(List<UserData> editors) { this.editors = editors; }

    public List<UserData> getReviewers() { return reviewers; }

    public void setReviewers(List<UserData> reviewers) { this.reviewers = reviewers; }

    public Status getMagazineStatus() { return magazineStatus; }

    public void setMagazineStatus(Status magazineStatus) { this.magazineStatus = magazineStatus; }
}
