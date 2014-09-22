package com.example.googleimagesearch.activities;

import com.example.googleimagesearch.models.ImageResult;
import com.example.googleimagesearch.activities.TouchImageView;
import com.squareup.picasso.Picasso;
import com.sushant2603.googleimagesearch.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult imageResult= (ImageResult) getIntent().getSerializableExtra("result");
		TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
		Picasso.with(this).load(imageResult.fullUrl).resize(600, 600).into(ivImageResult);
		getActionBar().hide();
		Toast.makeText(this, "Loaded Iamge", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
