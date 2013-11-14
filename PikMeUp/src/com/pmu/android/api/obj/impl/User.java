package com.pmu.android.api.obj.impl;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.storage.IStorage;
import com.pmu.android.api.storage.impl.StorageApi;

public class User {
	Context context;
	Request currentRequest;
	

	public Request getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(Request currentRequest) {
		this.currentRequest = currentRequest;
	}

	public User(Context newContext) {
		context = newContext;
	}

	public String getID() {
		IStorage pref = ApiFactory.getStrorageFactory(context).getPreferences();
		return (String) pref.getValue(StorageApi.ID);
	}

	public void setID(String newPin) {
		IStorage pref = ApiFactory.getStrorageFactory(context).getPreferences();
		pref.setValue(StorageApi.ID, newPin);
	}
	
	public String getPin() {
		IStorage pref = ApiFactory.getStrorageFactory(context).getPreferences();
		return (String) pref.getValue(StorageApi.PIN);
	}

	public void setPin(String newPin) {
		IStorage pref = ApiFactory.getStrorageFactory(context).getPreferences();
		pref.setValue(StorageApi.PIN, newPin);
	}

}
