package com.pmu.android.api.transport.impl;

import java.io.File;

import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.pmu.android.api.transport.ITransportResponse;

public class FileUploadResponse implements ITransportResponse {

	UploadResult ur;
	String caller;
	File f;

	public FileUploadResponse(UploadResult uploadResult, String newCaller) {
		ur = uploadResult;
		caller = newCaller;
	}

	public FileUploadResponse(File file, String name, boolean done) {
		caller = name;
		f = file;
	}

	@Override
	public Object getResponse() {
		if (ur != null) {
			return ur;
		} else if (f != null) {
			return f;
		} else {
			return "Failed";
		}
	}

	@Override
	public String getCaller() {
		return caller;
	}

}
