package com.pmu.android.api.storage.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pmu.android.api.storage.IStorage;

public class StorageApi implements IStorage {

	private static final String NAME = StorageApi.class.getName();
	public static final String TOKEN = "token";
	public static final String ORDERID = "orderid";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String METHOD = "method";
	public static final String USERNAME = "user_name";
	public static final String ID = "user_id";
	public static final String PIN = "user_pin";
	Context context;
	SharedPreferences pref;

	public StorageApi(Context c) {
		context = c;
		pref = c.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	@Override
	public Object getValue(String key) {
		Object result = null;
		if (pref.contains(key)) {
			result = pref.getAll().get(key);
		}
		return result;
	}

	@Override
	public void setValue(String key, Object value) {
		Editor ed = pref.edit();
		if (value instanceof Integer) {
			ed.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			ed.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {
			ed.putString(key, (String) value);
		} else if (value instanceof Float) {
			ed.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			ed.putLong(key, (Long) value);
		} else if (value == null) {
			ed.remove(key);
		}
		ed.commit();
	}
}
