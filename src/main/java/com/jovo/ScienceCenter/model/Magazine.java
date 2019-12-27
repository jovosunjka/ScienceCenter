package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "magazine")
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

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

    public Magazine() {

    }

    public Magazine(String name, String username, String password, String merchantId, double membershipFee, Currency currency) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.merchantId = merchantId;
        this.membershipFee = membershipFee;
        this.currency = currency;
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
}
