package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Transaction;
import com.jovo.ScienceCenter.model.TransactionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long merchantOrderId;
    private double amount;
    private Currency currency;
    private LocalDateTime merchantTimestamp;
    private LocalDateTime timestamp;
    private String paymentType;
    private TransactionStatus status;


    public TransactionDTO() {

    }

    public TransactionDTO(Transaction transaction) {
        this.merchantOrderId = transaction.getMerchantOrderId();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.merchantTimestamp = transaction.getMerchantTimestamp();
        this.timestamp = transaction.getTimestamp();
        this.paymentType = transaction.getPaymentType();
        this.status = transaction.getStatus();
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

    public LocalDateTime getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
