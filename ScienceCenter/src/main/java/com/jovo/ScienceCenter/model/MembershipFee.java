package com.jovo.ScienceCenter.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "membership_fee")
public class MembershipFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "magazine_id", unique = false, nullable = false)
    private Long magazineId;

    @Column(name = "payer_id", unique = false, nullable = false)
    private Long payerId;

    @Column(name = "kp_transaction_id", unique = true, nullable = true)
    private Long kpTransactionId;

    @Column(name = "price", unique = false, nullable = false)
    private double price;

    @Column(name = "currency", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "paid", unique = false, nullable = false)
    private boolean paid;

    @Column(name = "valid_until", unique = false, nullable = false)
    private LocalDateTime validUntil;


    public MembershipFee() {

    }

    public MembershipFee(Long magazineId, Long payerId, double price, Currency currency) {
        this.magazineId = magazineId;
        this.payerId = payerId;
        this.price = price;
        this.currency = currency;
        this.timestamp = LocalDateTime.now();
        this.paid = false;
        this.validUntil = this.timestamp.plusDays(30);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getKpTransactionId() {
        return kpTransactionId;
    }

    public void setKpTransactionId(Long kpTransactionId) {
        this.kpTransactionId = kpTransactionId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getValidUntil() { return validUntil; }

    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
}
