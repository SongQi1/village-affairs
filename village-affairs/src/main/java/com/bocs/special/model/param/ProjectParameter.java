package com.bocs.special.model.param;

import java.util.List;

import core.support.ExtJSBaseParameter;

public class ProjectParameter extends ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3988899193070688562L;
	
	private String startDate;
	
	private String endDate;
	
	private List<Integer> statusIds;
	
	private List<Integer> levelIds;
	
	private List<Integer> typeIds;
	
	private String name;
	
	private String $like_title;

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

	

	public List<Integer> getStatusIds() {
		return statusIds;
	}

	public void setStatusIds(List<Integer> statusIds) {
		this.statusIds = statusIds;
	}

	public List<Integer> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(List<Integer> levelIds) {
		this.levelIds = levelIds;
	}

	public List<Integer> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(List<Integer> typeIds) {
		this.typeIds = typeIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String get$like_title() {
		return $like_title;
	}

	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}
	
	

}
