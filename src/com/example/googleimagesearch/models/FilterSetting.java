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
		size = "any";
		type = "any";
		color = "any";
		site = "";
		sizePos = 0;
		colorPos = 0;
		typePos = 0;
		sitePos = 0;
	}
}
