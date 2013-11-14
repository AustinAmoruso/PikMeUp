package com.pmu.android.api.transport.impl;

import org.ksoap2.serialization.SoapObject;

import com.pmu.android.api.transport.ITransportResponse;

public class SoapResponse implements ITransportResponse {

	SoapObject soap;
	String caller;

	public SoapResponse(SoapObject so, String newCaller) {
		soap = so;
		caller = newCaller;
	}

	@Override
	public Object getResponse() {
		return soap;
	}

	@Override
	public String getCaller() {
		return caller;
	}

}
