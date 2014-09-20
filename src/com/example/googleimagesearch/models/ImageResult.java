package com.example.googleimagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = 1L;
	public String fullUrl;
	public String thumbUrl;
	public String title;
	public int height;
	public int width;

	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			this.title = json.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int index = 0; index < array.length(); index++) {
			try {
				results.add(new ImageResult(array.getJSONObject(index)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
