package com.pmu.android.api.transport.impl;

import com.pmu.android.api.transport.ITransportResponse;

public abstract class BaseResponse implements ITransportResponse {

	private String caller;

	BaseResponse(String newCaller) {
		caller = newCaller;
	}

	@Override
	public abstract Object getResponse();

	@Override
	public String getCaller() {
		return caller;
	}

}
