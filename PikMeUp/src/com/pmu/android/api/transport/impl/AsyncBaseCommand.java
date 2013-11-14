package com.pmu.android.api.transport.impl;

import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public abstract class AsyncBaseCommand implements IAsyncTransportCommand {

	private ITransportCallBack callback;

	public AsyncBaseCommand(ITransportCallBack newCallback) {
		callback = newCallback;
	}

	@Override
	public abstract ITransportResponse Execute();

	@Override
	public ITransportCallBack getCallBack() {
		return callback;
	}

}
