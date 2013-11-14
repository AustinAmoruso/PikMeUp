package com.pmu.android.api.transport.impl;

import java.util.LinkedHashMap;

import org.ksoap2.serialization.SoapObject;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.transport.ITransportApi;
import com.pmu.android.api.transport.ITransportCommand;
import com.pmu.android.api.transport.ITransportResponse;

public class SoapCommand implements ITransportCommand {
	private String url;
	private String method;
	private String nameSpace;
	private LinkedHashMap<String, String> hmp;

	public SoapCommand(String newUrl, String newMethod, String newNameSpace,
			LinkedHashMap<String, String> newHmp) {
		url = newUrl;
		method = newMethod;
		nameSpace = newNameSpace;
		hmp = newHmp;
	}

	@Override
	public ITransportResponse Execute() {
		return new SoapResponse(CallMethod(), method);
	}

	private SoapObject CallMethod() {
		ITransportApi tp = ApiFactory.getTransportFactory().getTransportApi();
		return tp.callMethod(url, getMethod(), method, nameSpace, hmp);
	}

	private String getMethod() {
		return url.substring(0, url.lastIndexOf("/")) + "/" + method;
	}

}
