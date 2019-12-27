package com.jovo.ScienceCenter.dto;

public class RegistrationResultDTO {

	private String name;
	private boolean result;
	
	
	public RegistrationResultDTO() {
		
	}
	
	public RegistrationResultDTO(String name, boolean result) {
		this.name = name;
		this.result = result;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
