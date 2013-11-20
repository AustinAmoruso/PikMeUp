package com.pmu.android.ui.impl;

import java.util.HashMap;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pmu.android.R;
import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;

public class MapPickerFragment extends DialogFragment implements
		OnItemClickListener, ITransportCallBack, OnClickListener {

	MapView m;
	Location l;

	public void setLocation(Location newLocation) {
		l = newLocation;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.MapPickerFragment);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapadd, container);
		getDialog().setTitle("Enter Location");
		m = (MapView) view.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) view
				.findViewById(R.id.edtAddress);
		HashMap<String, String> loc = ApiFactory.getAdapterFactory()
				.getLocation().query(getActivity());
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.list_item, loc.get(Location.LAT), loc
						.get(Location.LONG), "500"));
		autoCompView.setOnItemClickListener(this);
		Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMapToCurrentCords();
	}

	private void setMapToCurrentCords() {
		HashMap<String, String> loc = ApiFactory.getAdapterFactory()
				.getLocation().query(getActivity());
		double lat = Double.valueOf(loc.get(Location.LAT));
		double lng = Double.valueOf(loc.get(Location.LONG));
		setMapCordinates(lat, lng, 13);
	}

	private void setMapCordinates(double lat, double lng, int zoom) {
		LatLng loc = new LatLng(lat, lng);
		GoogleMap gm = m.getMap();
		gm.clear();
		gm.setMyLocationEnabled(true);
		gm.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
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
		Location location = (Location) adapterView.getItemAtPosition(position);
		l.setAlias(location.getAlias());
		l.setRequestID(location.getRequestID());
		AsyncTransportCalls.placeDetails(l.getRequestID(), l, this);
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getResponse() != null) {
			if (response.getResponse() instanceof Location) {
				double lat = Double.valueOf(l.getLatitude());
				double lng = Double.valueOf(l.getLongitude());
				LatLng loc = new LatLng(lat, lng);
				GoogleMap gm = m.getMap();
				gm.clear();
				gm.setMyLocationEnabled(true);
				gm.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
				gm.addMarker(new MarkerOptions().title(l.getAlias()).position(
						loc));
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAdd) {
			if (l != null && l.getLatitude() != null
					&& l.getLatitude().length() > 0) {
				dismiss();
			}
		}
	}
}
