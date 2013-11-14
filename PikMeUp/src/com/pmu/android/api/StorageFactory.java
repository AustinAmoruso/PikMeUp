package com.pmu.android.api;

import android.content.Context;

import com.pmu.android.api.storage.IStorage;
import com.pmu.android.api.storage.impl.ConstantsApi;
import com.pmu.android.api.storage.impl.SettingsApi;
import com.pmu.android.api.storage.impl.StorageApi;

public class StorageFactory {

	private StorageApi storageApi;
	private SettingsApi settingsApi;
	private Context context;
	private ConstantsApi constantsApi;

	public StorageFactory(Context c) {
		context = c;
	}

	public IStorage getPreferences() {
		if (storageApi == null) {
			storageApi = new StorageApi(context);
		}
		return storageApi;
	}

	public SettingsApi getSettings() {
		if (settingsApi == null) {
			settingsApi = new SettingsApi(context);
		}
		return settingsApi;
	}

	public ConstantsApi getConstants() {
		if (constantsApi == null) {
			constantsApi = new ConstantsApi(context);
		}
		return constantsApi;
	}

}
