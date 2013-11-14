package com.pmu.android.api.transport;

import java.net.URL;
import java.util.LinkedHashMap;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Document;

import android.graphics.Bitmap;

public interface ITransportApi {

	byte[] get(String url);

	Bitmap getImage(String url);

	public Document getXML(URL url);

	public SoapObject callMethod(String url, String action, String methodName,
			String nameSpace, LinkedHashMap<String, String> paramters);

}
