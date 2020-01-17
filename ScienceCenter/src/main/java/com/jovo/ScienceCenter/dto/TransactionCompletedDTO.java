package com.jovo.ScienceCenter.dto;



public class TransactionCompletedDTO {
	private long merchantOrderId;
	private String status;
 
	public TransactionCompletedDTO() {
		
	}

	public TransactionCompletedDTO(long merchantOrderId, String status) {
		super();
		this.merchantOrderId = merchantOrderId;
		this.status = status;
	}

	public long getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(long merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
