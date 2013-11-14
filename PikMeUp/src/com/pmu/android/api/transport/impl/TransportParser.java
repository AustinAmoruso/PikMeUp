package com.pmu.android.api.transport.impl;

import org.json.JSONException;
import org.json.JSONObject;

public class TransportParser {

	public static boolean validToken(String json) {
		return true;
	}

	public static boolean invalidToken(JSONObject json) {
		if (json.has("error")) {
			try {
				return (json.getString("error").toLowerCase()
						.contains("invalid device token"))
						|| (json.getString("error")
								.equalsIgnoreCase("device token has been invalidated."));
			} catch (JSONException e) {
			}
		}
		return false;
	}

}
