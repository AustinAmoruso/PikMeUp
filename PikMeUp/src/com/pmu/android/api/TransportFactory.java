package com.pmu.android.api;

import android.content.Context;

import com.pmu.android.api.transport.ITransportApi;
import com.pmu.android.api.transport.impl.TransportApi;
import com.pmu.android.api.transport.impl.TransportQueue;

public class TransportFactory {

	ITransportApi tranpsortApi;

	TransportQueue tq;

	public ITransportApi getTransportApi() {
		if (tranpsortApi == null) {
			tranpsortApi = new TransportApi();
		}
		return tranpsortApi;
	}

	public TransportQueue getTransportQueque(Context context) {
		if (tq == null) {
			tq = new TransportQueue(context);
		}
		return tq;
	}

}
