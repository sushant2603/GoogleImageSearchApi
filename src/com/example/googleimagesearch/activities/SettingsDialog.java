package com.example.googleimagesearch.activities;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.googleimagesearch.models.FilterSetting;
import com.sushant2603.googleimagesearch.R;

public class SettingsDialog extends DialogFragment {

	private Spinner spSettingSize;
	private Spinner spSettingColor;
	private Spinner spSettingType;
	private EditText etSettingSite;
	private Button saveButton;
	private Button cancelButton;
	private FilterSetting filterSetting;
	private FilterSetting originalFilterSetting;
	public SettingsDialogListener listener;

    static SettingsDialog newInstance(FilterSetting settings) {
        SettingsDialog dialog = new SettingsDialog();
        Bundle args = new Bundle();
        args.putSerializable("filterSetting", settings);
        dialog.setArguments(args);
        return dialog;
    }

    public interface SettingsDialogListener {
    	void onFinishSettingsDialog(FilterSetting settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        filterSetting = (FilterSetting) getArguments().getSerializable("filterSetting");
        originalFilterSetting = new FilterSetting();
        etSettingSite = (EditText) view.findViewById(R.id.etSettingSite);
        spSettingSize = (Spinner) view.findViewById(R.id.spSettingsize);
        spSettingColor = (Spinner) view.findViewById(R.id.spSettingColor);
        spSettingType = (Spinner) view.findViewById(R.id.spSettingType);

        saveButton = (Button) view.findViewById(R.id.btnSettingSave);
        cancelButton = (Button) view.findViewById(R.id.btnSettingCancel);
        ArrayAdapter<CharSequence> aSettingColor = ArrayAdapter.createFromResource(getActivity(),
        		R.array.image_colors_array, android.R.layout.simple_spinner_item);

        spSettingColor.setAdapter(aSettingColor);
        spSettingColor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				filterSetting.colorPos = position;
				filterSetting.color = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				filterSetting.colorPos = 0;
				filterSetting.color = "any";
			}
		});

        ArrayAdapter<CharSequence> aSettingSize = ArrayAdapter.createFromResource(getActivity(),
        		R.array.image_size_array, android.R.layout.simple_spinner_item);
        spSettingSize.setAdapter(aSettingSize);
        spSettingSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				filterSetting.sizePos = position;
				filterSetting.size = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				filterSetting.sizePos = 0;
				filterSetting.size = "any";
			}
		});

        ArrayAdapter<CharSequence> aSettingType = ArrayAdapter.createFromResource(getActivity(),
        		R.array.image_type_array, android.R.layout.simple_spinner_item);
        spSettingType.setAdapter(aSettingType);
        spSettingType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				filterSetting.typePos = position;
				filterSetting.type = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				filterSetting.typePos = 0;
				filterSetting.type = "any";
			}
		});

        saveButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                filterSetting.site = etSettingSite.getText().toString();
    		    listener.onFinishSettingsDialog(filterSetting);
                // When button is clicked, call up to owning activity.
        		getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			    listener.onFinishSettingsDialog(new FilterSetting());
				getDialog().dismiss();
			}
		});

        if (filterSetting != null) {
        	originalFilterSetting.color = filterSetting.color;
        	originalFilterSetting.colorPos = filterSetting.colorPos;
        	originalFilterSetting.size = filterSetting.size;
        	originalFilterSetting.sizePos = filterSetting.sizePos;
        	originalFilterSetting.type = filterSetting.type;
        	originalFilterSetting.typePos = filterSetting.typePos;
        	originalFilterSetting.site = filterSetting.size;


	        etSettingSite.setText(filterSetting.site);
	        spSettingSize.setSelection(filterSetting.sizePos);
	        spSettingColor.setSelection(filterSetting.colorPos);
	        spSettingType.setSelection(filterSetting.typePos);
        } else {
        	filterSetting = new FilterSetting();
        }

        Window window = getDialog().getWindow();
        window.setSoftInputMode(
        		WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //Customizing the dialog features
        window.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 231, 186)));
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	// safety check
    	if (getDialog() == null) {
    		return;
    	}
    	//getDialog().getWindow().setLayout(400, 400);
    }
}
