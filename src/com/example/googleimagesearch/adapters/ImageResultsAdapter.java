package com.example.googleimagesearch.adapters;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sushant2603.googleimagesearch.R;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.example.googleimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
	private final LayoutInflater mLayoutInflater;
	private final Random mRandom;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_list_item_1, images);
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mRandom = new Random();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		DynamicHeightImageView ivImage = (DynamicHeightImageView) convertView.findViewById(R.id.ivImage);
		//TextView tvTitle = (DynamicHeightTextView) convertView.findViewById(R.id.tvTitle);
		double positionHeight = getRandomHeightRatio();
		ivImage.setHeightRatio(imageInfo.height / imageInfo.width);
		Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
		//tvTitle.setText(Html.fromHtml(imageInfo.title));
		return convertView;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
													// the width
	}
}
