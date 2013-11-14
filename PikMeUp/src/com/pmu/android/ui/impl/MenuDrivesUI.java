package com.pmu.android.ui.impl;

import com.pmu.android.Drives;
import com.pmu.android.R;

public class MenuDrivesUI extends MenuBaseItem {

	public MenuDrivesUI() {
		super("Drives", R.drawable.drive);
	}

	@Override
	public Object getContents() {
		return Drives.class.getName();
	}

}
