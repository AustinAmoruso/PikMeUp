package com.pmu.android.api;

import android.content.Context;

import com.pmu.android.api.adapter.IAdapter;
import com.pmu.android.api.adapter.ICameraApi;
import com.pmu.android.api.adapter.IMediaApi;
import com.pmu.android.api.adapter.IScreenApi;
import com.pmu.android.api.adapter.impl.CameraApi;
import com.pmu.android.api.adapter.impl.Device;
import com.pmu.android.api.adapter.impl.Location;
import com.pmu.android.api.adapter.impl.MediaApi;
import com.pmu.android.api.adapter.impl.Phone;
import com.pmu.android.api.adapter.impl.ScreenApi;

public class AdapterFactory {

	private static Phone phoneAdapter;
	private static Location locationAdapter;
	private static ICameraApi cameraApi;
	private static IMediaApi mediaApi;
	private static IScreenApi screenApi;
	private static Device device;

	public IScreenApi getScreen(Context c) {
		if (screenApi == null) {
			screenApi = new ScreenApi(c);
		}
		return screenApi;
	}

	public IMediaApi getMedia() {
		if (mediaApi == null) {
			mediaApi = new MediaApi();
		}
		return mediaApi;
	}

	public IAdapter getPhone() {
		if (phoneAdapter == null) {
			phoneAdapter = new Phone();
		}
		return phoneAdapter;
	}

	public IAdapter getLocation() {
		if (locationAdapter == null) {
			locationAdapter = new Location();
		}
		return locationAdapter;
	}

	public ICameraApi getCameraApi() {
		if (cameraApi == null) {
			cameraApi = new CameraApi();
		}
		return cameraApi;
	}

	public Device getDevice() {
		if (device == null) {
			device = new Device();
		}
		return device;
	}
}
