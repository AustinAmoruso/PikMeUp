package com.pmu.android.api.transport.impl;

import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;

public class AsyncImageCommand extends ImageCommand implements
		IAsyncTransportCommand {

	private ITransportCallBack callback;

	public AsyncImageCommand(String newUrl, ITransportCallBack newCallback) {
		super(newUrl);
		callback = newCallback;
	}

	@Override
	public ITransportCallBack getCallBack() {
		return callback;
	}

}
