package com.pmu.android.api.transport.impl;

import java.util.LinkedHashMap;

import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;

public class AsyncSoapCommand extends SoapCommand implements
		IAsyncTransportCommand {

	private ITransportCallBack callback;

	public AsyncSoapCommand(String newUrl, String newMethod,
			String newNameSpace, LinkedHashMap<String, String> newHmp,
			ITransportCallBack newCallback) {
		super(newUrl, newMethod, newNameSpace, newHmp);
		callback = newCallback;
	}

	@Override
	public ITransportCallBack getCallBack() {
		return callback;
	}

}
