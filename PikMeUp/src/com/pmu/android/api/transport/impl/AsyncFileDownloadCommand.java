package com.pmu.android.api.transport.impl;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class AsyncFileDownloadCommand implements IAsyncTransportCommand {

	String name;
	ITransportCallBack callback;
	File file;

	AsyncFileDownloadCommand(File f, String newName,
			ITransportCallBack newCallback) {
		name = newName;
		callback = newCallback;
		file = f;
	}

	@Override
	public ITransportResponse Execute() {
		AWSCredentials credential = new BasicAWSCredentials(
				TransportContants.ACCESS_KEY_ID,
				TransportContants.SECERT_ACCESS_KEY);
		TransferManager manager = new TransferManager(credential);
		Download download = manager.download(TransportContants.BUCKET, name,
				file);
		try {
			download.waitForCompletion();
			return new FileUploadResponse(file, name, true);
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
