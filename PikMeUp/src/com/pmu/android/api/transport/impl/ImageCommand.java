package com.pmu.android.api.transport.impl;

import android.graphics.Bitmap;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.transport.ITransportApi;
import com.pmu.android.api.transport.ITransportCommand;
import com.pmu.android.api.transport.ITransportResponse;

public class ImageCommand implements ITransportCommand {

	private String url;

	public ImageCommand(String newUrl) {
		url = newUrl;
	}

	@Override
	public ITransportResponse Execute() {
		return new ImageResponse(getBitmap(), url);
	}

	private Bitmap getBitmap() {
		ITransportApi tp = ApiFactory.getTransportFactory().getTransportApi();
		return tp.getImage(url);
	}

}
