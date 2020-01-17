package com.jovo.ScienceCenter.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "merchant_order_id", unique = true, nullable = false)
	private Long merchantOrderId;
	
	@Column(name = "amount", unique = false, nullable = false)
	private double amount;
    
	@Column(name = "currency", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Currency currency;
    
    @Column(name = "merchant_timestamp", unique = false, nullable = false)
    private LocalDateTime merchantTimestamp;
    
    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "payment_type", unique = false, nullable = true)
    private String paymentType;

	@Column(name = "status", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransactionStatus status;

	
	public Transaction() {
		
	}
	
	public Transaction(Long merchantOrderId, double amount, Currency currency, LocalDateTime merchantTimestamp,
			LocalDateTime timestamp, TransactionStatus status) {
		this.merchantOrderId = merchantOrderId;
		this.amount = amount;
		this.currency = currency;
		this.merchantTimestamp = merchantTimestamp;
		this.timestamp = timestamp;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
