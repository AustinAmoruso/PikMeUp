package com.pmu.android.ui.impl;

import com.pmu.android.R;
import com.pmu.android.Rides;

public class MenuRidesUI extends MenuBaseItem {

	public MenuRidesUI() {
		super("Rides", R.drawable.ride);
	}

	@Override
	public Object getContents() {
		return Rides.class.getName();
	}

}
