package com.pmu.android.ui.impl;

import android.widget.AutoCompleteTextView;

import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Location;

public class LocationUI implements IObjectCallback {

	private AutoCompleteTextView actv;
	private Location location;

	public LocationUI(AutoCompleteTextView newActv, Location newLocation) {
		actv = newActv;
		location = newLocation;
		location.addOnActionCallback(this);
	}

	public AutoCompleteTextView getActv() {
		return actv;
	}

	public void setActv(AutoCompleteTextView actv) {
		this.actv = actv;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public void onAction(String action, Object value) {
		if (action.equalsIgnoreCase(Location.ALIAS)) {
			actv.setText((String) value);
		}
	}

}
