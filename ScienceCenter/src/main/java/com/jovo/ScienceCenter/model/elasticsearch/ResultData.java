package com.jovo.ScienceCenter.model.elasticsearch;

import com.jovo.ScienceCenter.dto.ScientificPaperForSearchResultDTO;

public final class ResultData {
	
	private String title;
	private String keywords;
	private String location;
	private String highlight;
	private ScientificPaperForSearchResultDTO scientificPaper;
	
	public ResultData() {
		super();
	}

	public ResultData(String title, String keywords, String location, String highlight,
					  ScientificPaperForSearchResultDTO scientificPaper) {
		super();
		this.title = title;
		this.keywords = keywords;
		this.location = location;
		this.highlight = highlight;
		this.scientificPaper = scientificPaper;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public ScientificPaperForSearchResultDTO getScientificPaper() {
		return scientificPaper;
	}

	public void setScientificPaper(ScientificPaperForSearchResultDTO scientificPaper) {
		this.scientificPaper = scientificPaper;
	}
}
