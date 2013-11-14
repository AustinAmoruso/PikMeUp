package com.pmu.android.api.storage.impl;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.storage.IStorage;

public class SettingsApi {

	private Context context;
	public static final String MULTI_SCAN = "multiscan";
	public static final String VIBRATE = "vibrate";
	public static final String SOUND = "sound";
	public static final String PROMPTSCAN = "promptscan";
	public static final String PINLOCK = "pinlock";
	public static final String IMPROVE = "improve";
	public static final String INFOCACHING = "infocaching";
	public static final String IMAGECACHING = "imagecaching";
	public static final String NOTIFYDEALS = "notifydeals";
	public static final String NOTIFYTRACKING = "notifytracking";
	public static final String NOTIFYSHIPPING = "notifyshipping";
	public static final String NOTIFYORDER = "notifyorder";
	public static final String SCANDELAYTIME = "scandelaytime";
	public static final String SCANSVOLUME = "scanvolume";
	public static final String DATALIMIT = "datalimit";
	public static final String SHIPREADY = "Shipping_ready";
	public static final String PAYREADY = "payment_ready";
	public static final String PIN = "pin";
	public static final String DEMO = "demo";

	public SettingsApi(Context c) {
		context = c;
		loadDefaults();
	}

	private void loadDefaults() {
		flashDefualt(MULTI_SCAN, true);
		flashDefualt(VIBRATE, true);
		flashDefualt(SOUND, true);
	}

	private void flashDefualt(String name, Object val) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		if (store.getValue(name) == null) {
			store.setValue(name, val);
		}
	}

	public boolean getMultipleScans() {
		return getBoolean(MULTI_SCAN).booleanValue();
	}

	public void setMultipleScans(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(MULTI_SCAN, on);
	}

	public boolean getShipReady() {
		return getBoolean(SHIPREADY).booleanValue();
	}

	public void setShipReady(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(SHIPREADY, on);
	}

	public boolean getPayReady() {
		return getBoolean(PAYREADY).booleanValue();
	}

	public void setPayReady(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(PAYREADY, on);
	}

	public boolean getVibrate() {
		return getBoolean(VIBRATE);
	}

	public void setVibrate(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(VIBRATE, on);
	}

	public boolean getSound() {
		return getBoolean(SOUND);
	}

	public void setSound(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(SOUND, on);
	}

	public boolean getPromptScan() {
		return getBoolean(PROMPTSCAN);
	}

	public void setPromptScan(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(PROMPTSCAN, on);
	}

	public boolean getPinLock() {
		return getBoolean(PINLOCK);
	}

	public void setPinLock(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(PINLOCK, on);
	}

	public boolean getNotifyOrderComplete() {
		return getBoolean(NOTIFYORDER);
	}

	public void setNotifyOrderComplete(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(NOTIFYORDER, on);
	}

	public boolean getNotifyShipping() {
		return getBoolean(NOTIFYSHIPPING);
	}

	public void setNotifyShipping(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(NOTIFYSHIPPING, on);
	}

	public boolean getNotifyTacking() {
		return getBoolean(NOTIFYTRACKING);
	}

	public void setNotifyTracking(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(NOTIFYTRACKING, on);
	}

	public boolean getNotifyDeals() {
		return getBoolean(NOTIFYDEALS);
	}

	public void setNotifyDeals(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(NOTIFYDEALS, on);
	}

	public boolean getImageCaching() {
		return getBoolean(IMAGECACHING);
	}

	public void setImageCaching(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(IMAGECACHING, on);
	}

	public boolean getInformationCaching() {
		return getBoolean(INFOCACHING);
	}

	public void setInformationCaching(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(INFOCACHING, on);
	}

	public boolean getImproveSnapBi() {
		return getBoolean(IMPROVE);
	}

	public void setImpoveSnapBi(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(IMPROVE, on);
	}

	public int getDelayScans() {
		if (getInt(SCANDELAYTIME) == -1) {
			return 12;
		}
		return getInt(SCANDELAYTIME);
	}

	public void setDelayScans(int milsec) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(SCANDELAYTIME, milsec);
	}

	public int getScansVolume() {
		if (getInt(SCANSVOLUME) == -1) {
			return 50;
		}
		return getInt(SCANSVOLUME);
	}

	public void setScansVolume(int percent) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(SCANSVOLUME, percent);
	}

	public int getDataLimit() {
		if (getInt(DATALIMIT) == -1) {
			return 512;
		}
		return getInt(DATALIMIT);
	}

	public void setDataLimit(int mb) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(DATALIMIT, mb);
	}

	public String getPin() {
		return getString(PIN);
	}

	public void setPin(String pin) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(PIN, pin);
	}

	private Integer getInt(String name) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		if (store.getValue(name) != null) {
			try {
				return (Integer) store.getValue(name);
			} catch (Exception e) {
			}
		}
		return -1;
	}

	private String getString(String name) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		if (store.getValue(name) != null) {
			return String.valueOf(store.getValue(name));
		}
		return "";
	}

	private Boolean getBoolean(String name) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		if (store.getValue(name) != null) {
			try {
				return (Boolean) store.getValue(name);
			} catch (Exception e) {
			}
		}
		return false;
	}

	public boolean getDemoMode() {
		return getBoolean(DEMO);
	}

	public void setDemoMode(boolean on) {
		IStorage store = ApiFactory.getStrorageFactory(context)
				.getPreferences();
		store.setValue(DEMO, on);
	}
}
