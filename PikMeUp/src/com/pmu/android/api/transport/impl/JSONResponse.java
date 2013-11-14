package com.pmu.android.api.transport.impl;

import org.json.JSONObject;

public class JSONResponse extends BaseResponse {

	JSONObject json;

	public JSONResponse(JSONObject newJson, String caller) {
		super(caller);
		json = newJson;
	}

	@Override
	public Object getResponse() {
		return json;
	}

}
