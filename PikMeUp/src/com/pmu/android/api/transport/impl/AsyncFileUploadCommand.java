package com.pmu.android.api.transport.impl;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class AsyncFileUploadCommand implements IAsyncTransportCommand {

	File file;
	String name;
	ITransportCallBack callback;

	AsyncFileUploadCommand(File newFile, String newName,
			ITransportCallBack newCallback) {
		file = newFile;
		name = newName;
		callback = newCallback;
	}

	@Override
	public ITransportResponse Execute() {
		AWSCredentials credential = new BasicAWSCredentials(
				TransportContants.ACCESS_KEY_ID,
				TransportContants.SECERT_ACCESS_KEY);
		TransferManager manager = new TransferManager(credential);
		Upload upload = manager.upload(TransportContants.BUCKET, name, file);
		try {
			return new FileUploadResponse(upload.waitForUploadResult(), name);
		} catch (Exception e) {
			e.printStackTrace();
			return new FileUploadResponse(null, name);
		}
	}

	@Override
	public ITransportCallBack getCallBack() {
		return callback;
	}

}
