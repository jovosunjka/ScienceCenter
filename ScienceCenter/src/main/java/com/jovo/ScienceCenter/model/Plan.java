package com.jovo.ScienceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "interval_unit", unique = false, nullable = false)
	private String intervalUnit;
	
	@Column(name = "interval_count", unique = false, nullable = false)
	private int intervalCount;
	
	@Column(name = "price", unique = false, nullable = false)
	private double price;
	
	public Plan() {}
	
	public Plan(String unit, int count) {
		this.intervalCount = count;
		this.intervalUnit = unit;
	}

	public Plan(String unit, int count, double price) {
		this.intervalCount = count;
		this.intervalUnit = unit;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
