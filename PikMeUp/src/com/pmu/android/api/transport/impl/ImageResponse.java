package com.pmu.android.api.transport.impl;

import android.graphics.Bitmap;

import com.pmu.android.api.transport.ITransportResponse;

public class ImageResponse implements ITransportResponse {

	Bitmap bp;
	String caller;

	public ImageResponse(Bitmap newBitmap, String newCaller) {
		bp = newBitmap;
		caller = newCaller;
	}

	@Override
	public Object getResponse() {
		return bp;
	}

	@Override
	public String getCaller() {
		return caller;
	}

}
