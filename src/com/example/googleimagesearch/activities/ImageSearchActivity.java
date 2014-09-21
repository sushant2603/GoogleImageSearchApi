package com.example.googleimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.example.googleimagesearch.activities.SettingsDialog.SettingsDialogListener;
import com.example.googleimagesearch.adapters.ImageResultsAdapter;
import com.example.googleimagesearch.models.FilterSetting;
import com.example.googleimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sushant2603.googleimagesearch.R;

public class ImageSearchActivity extends FragmentActivity {
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private FilterSetting filterSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setUpViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
		filterSettings = new FilterSetting();
        gvResults.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	            // Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
		        GetResults(totalItemsCount); 
		    }
        });
	}

	private void setUpViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(ImageSearchActivity.this, ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				i.putExtra("result", result);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	private void GetResults(int start) {
		String query = etQuery.getText().toString();
    	AsyncHttpClient client = new AsyncHttpClient();
    	String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
    			+ query + "&rsz=8" + "&start=" + Integer.toString(start);
		if (!filterSettings.color.isEmpty()) {
			searchUrl += "&imgcolor=" + filterSettings.color;
		}
		if (!filterSettings.size.isEmpty()) {
			searchUrl += "&imgsz=" + filterSettings.size;
		}
		if (!filterSettings.type.isEmpty()) {
			searchUrl += "&imgtype=" + filterSettings.type;
		}
		if (!filterSettings.site.isEmpty()) {
			searchUrl += "&as_sitesearch=" + filterSettings.site;
		}
    	client.get(searchUrl, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,
    				JSONObject response) {
    			Log.d("DEBUG", response.toString());
    			JSONArray imageResultsJson;
    			try {
    				imageResultsJson= response.getJSONObject("responseData").getJSONArray("results");
    				// TODO: Handle this for pagination.
    				aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			super.onSuccess(statusCode, headers, response);
    		}
    	});
	}

	// Fired when search button is clicked.
	public void onImageSearch(View view) {
		aImageResults.clear();
		GetResults(0);
	}

	public void onSettingsAction(MenuItem mi) {
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);
		SettingsDialog dialog = SettingsDialog.newInstance(filterSettings);
		dialog.show(ft, "settings");
		dialog.listener = new SettingsDialogListener() {

			@Override
			public void onFinishSettingsDialog(FilterSetting settings) {
				filterSettings = settings;
				GetResults(0);
			}
		};
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
