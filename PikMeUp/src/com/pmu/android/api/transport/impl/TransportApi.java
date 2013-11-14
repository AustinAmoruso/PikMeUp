package com.pmu.android.api.transport.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pmu.android.api.transport.ITransportApi;
import com.pmu.android.util.SoapParser;
import com.pmu.android.util.Util;

public class TransportApi implements ITransportApi {

	public byte[] get(String url) {
		try {
			byte[] result = null;
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			while (bis.read(buffer) != -1) {
				if (result != null) {
					result = Util.combine(result, buffer);
				} else {
					result = buffer;
				}
			}
			bis.close();
			is.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public Document getXML(URL url) {
		Document doc = null;
		HttpGet uri = null;
		try {
			uri = new HttpGet(url.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try {
			resp = client.execute(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatusLine status = resp.getStatusLine();
		if (status.getStatusCode() == HttpStatus.SC_OK) {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder;
				builder = factory.newDocumentBuilder();
				doc = builder.parse(resp.getEntity().getContent());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	public Bitmap getImage(String url) {
		try {
			Bitmap result = null;
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			try {
				conn.connect();
			} catch (Exception e) {
				return null;
			}
			InputStream is = conn.getInputStream();
			if (is != null) {
				try {
					BufferedInputStream bis = new BufferedInputStream(is);
					if (bis != null) {
						try {
							result = BitmapFactory.decodeStream(is);
						} catch (Exception e) {
						}
					}
					bis.close();
				} catch (Exception e) {
				}
				is.close();
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public SoapObject callMethod(String url, String action, String methodName,
			String nameSpace, LinkedHashMap<String, String> paramters) {
		try {
			Log.i("URL", url);
			Log.i("action", action);
			Log.i("methodName", methodName);
			Log.i("nameSpace", nameSpace);
		} catch (Exception e) {
		}
		SoapObject result = null;
		SoapObject request = new SoapObject(nameSpace, methodName);
		if (paramters != null) {
			for (String k : paramters.keySet()) {
				try {
					Log.i(k, paramters.get(k));
				} catch (Exception e) {

				}
				request.addProperty(k, paramters.get(k));
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

		try {
			androidHttpTransport.call(action, envelope);
			result = (SoapObject) envelope.bodyIn;
			try {
				Log.i("body", SoapParser.getContent(result));
			} catch (Exception e) {
			}
		} catch (Exception e) {
			try {
				SoapFault sf = (SoapFault) envelope.bodyIn;
				result = new SoapObject("Fault", sf.getMessage());
				Log.d("************", sf.getLocalizedMessage());
				String fault = sf.getMessage();
				Log.d("************", fault);
				e.printStackTrace();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		return result;
	}

}
