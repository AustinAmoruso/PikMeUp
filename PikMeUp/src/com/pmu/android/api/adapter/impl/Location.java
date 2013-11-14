package com.pmu.android.api.adapter.impl;

import java.util.HashMap;

import android.content.Context;
import android.location.LocationManager;

import com.pmu.android.api.adapter.IAdapter;

public class Location implements IAdapter {

	public static final String LAT = "lat";
	public static final String LONG = "long";

	@Override
	public HashMap<String, String> query(Context c) {
		HashMap<String, String> result = new HashMap<String, String>();
		LocationManager locationManager = (LocationManager) c
				.getSystemService(Context.LOCATION_SERVICE);
		android.location.Location loc = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (loc != null) {
			result.put(LAT, String.valueOf(loc.getLatitude()));
			result.put(LONG, String.valueOf(loc.getLongitude()));
		} else {
			result.put(LAT, "0.0");
			result.put(LONG, "0.0");
		}
		return result;
	}
}
