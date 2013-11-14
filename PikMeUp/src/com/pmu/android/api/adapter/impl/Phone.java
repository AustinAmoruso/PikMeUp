package com.pmu.android.api.adapter.impl;

import java.util.HashMap;

import android.content.Context;
import android.os.Build;

import com.pmu.android.api.adapter.IAdapter;

public class Phone implements IAdapter {

	public static final String IMEI = "imei";
	public static final String NUMBER = "number";
	public static final String FINERPRINT = "finerprint";
	private static final String VERSION = "version";
	public static final String BRAND = "brand";
	public static final String ID = "id";
	public static final String RADIO = "radio";
	public static final String SERIAL = "serial";
	public static final String MANUFACTURE = "make";
	public static final String MODEL = "model";
	HashMap<String, String> specs;

	@Override
	public HashMap<String, String> query(Context c) {
		specs = new HashMap<String, String>();
		specs.put(MODEL, Build.MODEL);
		specs.put(MANUFACTURE, Build.MANUFACTURER);
		specs.put(VERSION, Build.VERSION.RELEASE);
		return specs;
	}

}
