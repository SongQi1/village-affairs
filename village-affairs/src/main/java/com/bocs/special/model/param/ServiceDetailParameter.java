package com.bocs.special.model.param;

import core.support.ExtJSBaseParameter;

public class ServiceDetailParameter  extends ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1880172288257199051L;

	
	private String startDate;
	
	private String endDate;

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
	
	
}
