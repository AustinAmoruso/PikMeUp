package com.pmu.android.api.transport.impl;

import com.pmu.android.api.transport.ITransportResponse;

public class GCMResponse implements ITransportResponse {

	String regID;
	String senderID;

	public GCMResponse(String newRegID, String newSenderID) {
		regID = newRegID;
		senderID = newSenderID;
	}

	@Override
	public Object getResponse() {
		return regID;
	}

	@Override
	public String getCaller() {
		return senderID;
	}

}
