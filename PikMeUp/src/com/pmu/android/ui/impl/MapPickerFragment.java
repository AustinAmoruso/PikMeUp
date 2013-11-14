package com.pmu.android.ui.impl;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.pmu.android.R;

public class MapPickerFragment extends DialogFragment implements
		OnItemClickListener {

	MapView m;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.MapPickerFragment);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapadd, container);
		getDialog().setTitle("Start Location");
		m = (MapView) view.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) view
				.findViewById(R.id.edtAddress);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.list_item));
		autoCompView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		m.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		m.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		m.onLowMemory();
	}

	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
	}
}
