package com.example.googleimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;
import com.sushant2603.googleimagesearch.R;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.example.googleimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
	//private final LayoutInflater mLayoutInflater;

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_list_item_1, images);
		//this.mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		DynamicHeightImageView ivImage = (DynamicHeightImageView) convertView.findViewById(R.id.ivImage);
		float heightRatio =  (imageInfo.height / imageInfo.width);
		ivImage.setHeightRatio(heightRatio);
		ivImage.setImageResource(0);
		Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
		return convertView;
	}
}
