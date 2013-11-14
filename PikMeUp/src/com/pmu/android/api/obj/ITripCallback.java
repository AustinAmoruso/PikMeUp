package com.pmu.android.api.obj;

public interface ITripCallback {
	String getCallbackName();
	
	void onDataChange(String name, Object value);
}