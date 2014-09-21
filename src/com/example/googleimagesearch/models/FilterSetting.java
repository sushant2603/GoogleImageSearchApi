package com.example.googleimagesearch.models;

import java.io.Serializable;

public class FilterSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	public String size;
	public String type;
	public String color;
	public String site;
	public int sizePos;
	public int colorPos;
	public int typePos;
	public int sitePos;

	public FilterSetting() {
		size = "";
		type = "";
		color = "";
		site = "";
		sizePos = -1;
		colorPos = -1;
		typePos = -1;
		sitePos = -1;
	}
}
