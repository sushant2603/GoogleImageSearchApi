package com.example.googleimagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.googleimagesearch.models.ImageResult;
import com.example.googleimagesearch.activities.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sushant2603.googleimagesearch.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ImageDisplayActivity extends Activity {

	private ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult imageResult= (ImageResult) getIntent().getSerializableExtra("result");
		TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
		// Get the main image and modified it to display with right aspect ratio.
        int newWidth = 600;
        int newHeight = (int) (newWidth * imageResult.width / imageResult.height);
		Picasso.with(this).load(imageResult.fullUrl).resize(newWidth, newHeight).into(ivImageResult, new Callback() {
	        @Override
	        public void onSuccess() {
	            // Setup share intent now that image has loaded
	        	if (miShareAction != null) {
	        		setupShareIntent();
	        	}
	        }
	        
	        @Override
	        public void onError() { 
	            Toast.makeText(ImageDisplayActivity.this, "Image size too large", Toast.LENGTH_SHORT).show();
	        }
	   });
		//getActionBar().hide();
	}

	// Gets the image URI and setup the associated share intent to hook into the provider
	public void setupShareIntent() {
	    // Fetch Bitmap Uri locally
	    TouchImageView ivImage = (TouchImageView) findViewById(R.id.ivImageResult);
	    Uri bmpUri = getLocalBitmapUri(ivImage); // see previous remote images section
	    // Create share intent as described above
	    Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	    shareIntent.setType("image/*");
	    // Attach share event to the menu item provider
	    miShareAction.setShareIntent(shareIntent);
	} 

	public void onShareItem(MenuItem mi) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
	    TouchImageView ivImage = (TouchImageView) findViewById(R.id.ivImageResult);
        Uri uri = getLocalBitmapUri(ivImage);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        startActivity(Intent.createChooser(shareIntent, "Share image using"));
	}

	// Returns the URI path to the Bitmap displayed in specified ImageView
	public Uri getLocalBitmapUri(TouchImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		MenuItem item = menu.findItem(R.id.menu_item_share);
		miShareAction = (ShareActionProvider) item.getActionProvider();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		miShareAction = (ShareActionProvider) item.getActionProvider();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
