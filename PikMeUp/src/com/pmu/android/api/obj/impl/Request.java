package com.pmu.android.api.obj.impl;

import org.json.JSONObject;

import com.pmu.android.api.storage.ISerialize;

public class Request implements ISerialize {

	private Location start;
	private Location end;
	private String time;
	private String flex;
	private String type;
	private String request_ID;

	public Location getStart() {
		return start;
	}

	public void setStart(Location start) {
		this.start = start;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFlex() {
		return flex;
	}

	public void setFlex(String flex) {
		this.flex = flex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequest_ID() {
		return request_ID;
	}

	public void setRequest_ID(String request_ID) {
		this.request_ID = request_ID;
	}

	@Override
	public String getJSONString() {
		return getJSONObject().toString();
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject result = new JSONObject();
		try {
			JSONObject request = new JSONObject();
			request.put("start", start.getJSONObject());
			request.put("end", end.getJSONObject());
			request.put("time", time);
			request.put("flex", flex);
			result.put(type, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
