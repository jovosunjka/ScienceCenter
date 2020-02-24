package com.jovo.ScienceCenter.dto;

import java.util.List;

public class QueriesDTO {

	private List<SimpleQueryWithOperator> queries;


	public QueriesDTO() {

	}

	public QueriesDTO(List<SimpleQueryWithOperator> queries) {
		this.queries = queries;
	}


	public List<SimpleQueryWithOperator> getQueries() {
		return queries;
	}

	public void setQueries(List<SimpleQueryWithOperator> queries) {
		this.queries = queries;
	}
}
