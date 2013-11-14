package com.pmu.android.ui.impl;

import com.pmu.android.Help;
import com.pmu.android.R;

public class MenuHelpUI extends MenuBaseItem {

	public MenuHelpUI() {
		super("Help", R.drawable.help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getContents() {
		return Help.class.getName();
	}

}
