package com.jovo.ScienceCenter.dto;

public class PlanDTO {

	private String intervalUnit;
	private int intervalCount;
	private	double price;
	
	public PlanDTO() {}
	
	public PlanDTO(String intervalUnit, int intervaCount) {
		this.intervalCount = intervaCount;
		this.intervalUnit = intervalUnit;
	}
	
	public PlanDTO(String intervalUnit, int intervaCount, double price) {
		this.intervalCount = intervaCount;
		this.intervalUnit = intervalUnit;
		this.price = price;
	}

	public String getIntervalUnit() {
		return intervalUnit;
	}

	public void setIntervalUnit(String intervalUnit) {
		this.intervalUnit = intervalUnit;
	}

	public int getIntervalCount() {
		return intervalCount;
	}

	public void setIntervalCount(int intervalCount) {
		this.intervalCount = intervalCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
