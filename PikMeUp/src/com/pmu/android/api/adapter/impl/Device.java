package com.pmu.android.api.adapter.impl;

import java.util.HashMap;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.adapter.IAdapter;

public class Device implements IAdapter {

	private HashMap<String, String> location;

	@Override
	public HashMap<String, String> query(Context c) {
		HashMap<String, String> result = new HashMap<String, String>();
		location = ApiFactory.getAdapterFactory().getLocation().query(c);
		result.putAll(location);
		return result;
	}

}
