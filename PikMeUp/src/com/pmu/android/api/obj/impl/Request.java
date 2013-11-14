package com.pmu.android.api.obj.impl;

import org.json.JSONObject;

import com.pmu.android.api.storage.ISerialize;

public class Request extends BaseAsyncObject implements ISerialize {

	public static final String REQUEST_ID = "request_ID";
	public static final String TYPE = "type";
	public static final String FLEX = "flex";
	public static final String TIME = "time";
	public static final String END = "end";
	public static final String START = "start";
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
		start.addOnActionCallback(this);
		onUpdate(START, start);
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
		end.addOnActionCallback(this);
		onUpdate(END, end);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
		onUpdate(TIME, time);
	}

	public String getFlex() {
		return flex;
	}

	public void setFlex(String flex) {
		this.flex = flex;
		onUpdate(FLEX, flex);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		onUpdate(TYPE, type);
	}

	public String getRequest_ID() {
		return request_ID;
	}

	public void setRequest_ID(String request_ID) {
		this.request_ID = request_ID;
		onUpdate(REQUEST_ID, request_ID);
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
			request.put(START, start.getJSONObject());
			request.put(END, end.getJSONObject());
			request.put(TIME, time);
			request.put(FLEX, flex);
			result.put(type, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
