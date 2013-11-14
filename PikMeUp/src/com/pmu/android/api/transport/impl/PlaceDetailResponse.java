package com.pmu.android.api.transport.impl;

import com.pmu.android.api.obj.impl.Location;

public class PlaceDetailResponse extends BaseResponse {

	Location location;

	public PlaceDetailResponse(Location newLocation, String caller) {
		super(caller);
		location = newLocation;
	}

	@Override
	public Object getResponse() {
		return location;
	}

}
