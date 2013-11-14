package com.pmu.android.api.transport.impl;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class JSONCommand extends AsyncBaseCommand {

	private String url;

	JSONCommand(String newUrl, ITransportCallBack callback) {
		super(callback);
		url = newUrl;
	}

	@Override
	public ITransportResponse Execute() {

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			return new JSONResponse(jsonObj, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONResponse(null, url);
	}

}
