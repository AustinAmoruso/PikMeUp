package com.pmu.android.api.storage.impl;

import android.content.Context;

public class ConstantsApi {

	private Context context;

	public ConstantsApi(Context newContext) {
		context = newContext;
	}

	public String getValue(int id) {
		String result = "";
		try {
			context.getString(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
