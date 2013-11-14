package com.pmu.android.api.transport.impl;

import android.os.AsyncTask;

import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class AsyncTransportLayer extends
		AsyncTask<IAsyncTransportCommand, Integer, ITransportResponse> {

	private ITransportCallBack tcb;

	public AsyncTransportLayer() {
	}

	@Override
	protected ITransportResponse doInBackground(
			IAsyncTransportCommand... params) {
		ITransportResponse tr = null;
		for (IAsyncTransportCommand atc : params) {
			tcb = atc.getCallBack();
			tr = atc.Execute();
		}
		return tr;
	}

	protected void onPostExecute(ITransportResponse response) {
		if (response != null && tcb != null) {
			tcb.onCallback(response);
		}
	}
}
