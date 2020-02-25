package com.jovo.ScienceCenter.model.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.elasticsearch.annotations.Setting;


@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
//@Setting(settingPath = "/elasticsearch/uputstvo.txt")
public class IndexUnit {

	public static final String INDEX_NAME = "index_for_scientific_paper";
	public static final String TYPE_NAME = "scientific_paper";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	@Field(type = FieldType.Text/*, analyzer = "serbian_analyzer", searchAnalyzer = "serbian_analyzer"*/)
	private String text;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Text)
	private String keywords;

	@Field(type = FieldType.Text)
	private String authors;

	@Field(type = FieldType.Text)
	private String magazinename;

	@Field(type = FieldType.Text)
	private String scientificarea;

	@Id
	@Field(type = FieldType.Text)
	private String filename;

	@Field(type = FieldType.Text)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
	private String filedate;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiledate() {
		return filedate;
	}

	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getMagazinename() {
		return magazinename;
	}

	public void setMagazinename(String magazinename) {
		this.magazinename = magazinename;
	}

	public String getScientificarea() {
		return scientificarea;
	}

	public void setScientificarea(String scientificarea) {
		this.scientificarea = scientificarea;
	}
}
