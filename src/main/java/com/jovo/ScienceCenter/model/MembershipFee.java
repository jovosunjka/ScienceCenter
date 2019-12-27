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

    @Column(name = "price", unique = false, nullable = false)
    private double price;

    @Column(name = "currency", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "duration", unique = false, nullable = false)
    private int duration; // months

    @Column(name = "expired", unique = false,nullable = false)
    private boolean expired;

    public MembershipFee(Long magazineId, Long payerId, double price,
                         Currency currency, LocalDateTime timestamp, int duration) {
        this.magazineId = magazineId;
        this.payerId = payerId;
        this.price = price;
        this.currency = currency;
        this.timestamp = timestamp;
        this.duration = duration;
        this.expired = false;
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}