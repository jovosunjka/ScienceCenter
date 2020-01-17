package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Currency;

import java.time.LocalDateTime;

public class PayDTO {
    private Long merchantOrderId;
    private double amount;
    private Currency currency;
    private LocalDateTime timestamp;
    private String redirectUrl;
    private String callbackUrl;


    public PayDTO(Long merchantOrderId, double amount, Currency currency, String redirectUrl, String callbackUrl) {
        this.merchantOrderId = merchantOrderId;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = LocalDateTime.now();
        this.redirectUrl = redirectUrl;
        this.callbackUrl = callbackUrl;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
