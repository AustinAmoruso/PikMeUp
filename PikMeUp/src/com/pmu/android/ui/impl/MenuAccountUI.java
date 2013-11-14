package com.pmu.android.ui.impl;

import com.pmu.android.Account;
import com.pmu.android.R;

public class MenuAccountUI extends MenuBaseItem {

	public MenuAccountUI() {
		super("Account", R.drawable.account);
	}

	@Override
	public Object getContents() {
		return Account.class.getName();
	}

}
