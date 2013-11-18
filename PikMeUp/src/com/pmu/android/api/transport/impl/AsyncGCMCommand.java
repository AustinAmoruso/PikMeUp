package com.pmu.android.api.transport.impl;

import java.io.IOException;

import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class AsyncGCMCommand extends AsyncBaseCommand {

	private Context context;
	private String senderID;

	public AsyncGCMCommand(ITransportCallBack newCallback, Context newContext,
			String newSenderID) {
		super(newCallback);
		context = newContext;
		senderID = newSenderID;
	}

	@Override
	public ITransportResponse Execute() {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		String regId;
		try {
			regId = gcm.register(senderID);

		} catch (IOException e) {
			e.printStackTrace();
			regId = "";
		}
		return new GCMResponse(regId, senderID);
	}
}
