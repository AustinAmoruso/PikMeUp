package com.pmu.android.api.transport.impl;

import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.pmu.android.api.transport.ITransportResponse;

public class FileUploadResponse implements ITransportResponse {

	UploadResult ur;
	String caller;

	public FileUploadResponse(UploadResult uploadResult, String newCaller) {
		ur = uploadResult;
		caller = newCaller;
	}

	@Override
	public Object getResponse() {
		if (ur != null) {
			return ur;
		} else {
			return "Failed";
		}
	}

	@Override
	public String getCaller() {
		return caller;
	}

}
