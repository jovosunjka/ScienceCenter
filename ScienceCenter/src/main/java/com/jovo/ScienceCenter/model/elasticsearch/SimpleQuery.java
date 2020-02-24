package com.jovo.ScienceCenter.model.elasticsearch;

public class SimpleQuery {
	
	protected String field;
	protected String value;
	
	public SimpleQuery() {
		super();
	}


	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
