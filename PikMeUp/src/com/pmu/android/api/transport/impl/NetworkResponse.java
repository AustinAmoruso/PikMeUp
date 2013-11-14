package com.pmu.android.api.transport.impl;

import com.pmu.android.api.transport.ITransportResponse;

public class NetworkResponse implements ITransportResponse {

	public static final String UNAVAILABLE = "unavailable";
	public static final String AVAILABLE = "available";
	private String status;

	public NetworkResponse(String newStatus) {
		status = newStatus;
	}

	@Override
	public Object getResponse() {
		return status;
	}

	@Override
	public String getCaller() {
		return getClass().getName();
	}

}
