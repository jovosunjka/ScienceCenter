package com.jovo.ScienceCenter.dto;

import java.util.List;


public class ProductDTO {
	
	private String name;
	
	private List<PlanDTO> plans;

	public ProductDTO() {}
	
	public ProductDTO(List<PlanDTO> plans, String name) {
		this.name = name;
		this.plans = plans;
	}

	public List<PlanDTO> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanDTO> plans) {
		this.plans = plans;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
