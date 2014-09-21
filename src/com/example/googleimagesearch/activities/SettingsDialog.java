package com.example.googleimagesearch.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

	/*private TextView tvSettingSize;
	private TextView tvSettingColor;
	private TextView tvSettingType;
	private TextView tvSettingSite;*/
	private Spinner spSettingSize;
	private Spinner spSettingColor;
	private Spinner spSettingType;
	private EditText etSettingSite;
	private Button saveButton;
	private FilterSetting filterSetting;
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
        /*tvSettingSite = (TextView) view.findViewById(R.id.tvSettingSite);
        tvSettingColor = (TextView) view.findViewById(R.id.tvSettingColor);
        tvSettingSize = (TextView) view.findViewById(R.id.tvSettingSize);
        tvSettingType = (TextView) view.findViewById(R.id.tvSettingType);*/
        filterSetting = (FilterSetting) getArguments().getSerializable("filterSetting");
        etSettingSite = (EditText) view.findViewById(R.id.etSettingSite);
        spSettingSize = (Spinner) view.findViewById(R.id.spSettingsize);
        spSettingColor = (Spinner) view.findViewById(R.id.spSettingColor);
        spSettingType = (Spinner) view.findViewById(R.id.spSettingType);

        if (filterSetting != null) {
	        etSettingSite.setText(filterSetting.site);
	        spSettingSize.setSelection(filterSetting.sizePos);
	        spSettingColor.setSelection(filterSetting.colorPos);
	        spSettingType.setSelection(filterSetting.typePos);
        }
        saveButton = (Button) view.findViewById(R.id.btnSettingSave);
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
				filterSetting.colorPos = -1;
				filterSetting.color = "Any";
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
				filterSetting.sizePos = -1;
				filterSetting.size = "Any";
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
				filterSetting.typePos = -1;
				filterSetting.type = "Any";
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

        getDialog().getWindow().setSoftInputMode(
        		WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    /*@Override
    public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		    SettingsDialogListener listener = (SettingsDialogListener) activity;
		} catch (ClassCastException e) {
		    throw new ClassCastException(activity.toString()
		            + " must implement NoticeDialogListener");
		}
    }*/
}
