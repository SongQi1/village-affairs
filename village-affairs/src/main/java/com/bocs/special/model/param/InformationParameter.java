package com.bocs.special.model.param;

import java.util.List;

import core.support.ExtJSBaseParameter;

public class InformationParameter extends ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5249965900189600670L;
	
	
	private String contentNoHTML;

	
	private String startDate;
	
	private String endDate;
	
	private List<String> types;
	
	private List<String> communities;

	public String getContentNoHTML() {
		return contentNoHTML;
	}


	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public List<String> getTypes() {
		return types;
	}


	public void setTypes(List<String> types) {
		this.types = types;
	}


	public List<String> getCommunities() {
		return communities;
	}


	public void setCommunities(List<String> communities) {
		this.communities = communities;
	}

	
}
