package com.bocs.special.dto;

public class Epie {

	private String title;
	private String subTitle;
	
	private String[] legendData;
	
	private PieSeriesData[] seriesData;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String[] getLegendData() {
		return legendData;
	}

	public void setLegendData(String[] legendData) {
		this.legendData = legendData;
	}

	public PieSeriesData[] getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(PieSeriesData[] seriesData) {
		this.seriesData = seriesData;
	}
	
	
	
	
}
