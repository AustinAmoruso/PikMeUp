package com.pmu.android.api.obj.impl;

import org.json.JSONObject;

import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.storage.ISerialize;

public class Request extends BaseAsyncObject implements ISerialize, IFeedObject {

	public static final String REQUEST_ID = "request_ID";
	public static final String TYPE = "type";
	public static final String FLEX = "flex";
	public static final String TIME = "time";
	public static final String STOP = "stop";
	public static final String START = "start";
	public static final String DRIVE = "drive";
	public static final String RIDE = "ride";
	public static final String TRIP = "trip";
	private Location start;
	private Location stop;
	private String flex;
	private String type;
	private String request_ID;
	private Time time;
	private String duration;

	public Location getStart() {
		return start;
	}

	public void setStart(Location start) {
		this.start = start;
		start.addOnActionCallback(this);
		onUpdate(START, start);
	}

	public Location getEnd() {
		return stop;
	}

	public void setEnd(Location end) {
		this.stop = end;
		end.addOnActionCallback(this);
		onUpdate(STOP, end);
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

	public String getID() {
		return request_ID;
	}

	public void setID(String request_ID) {
		this.request_ID = request_ID;
		onUpdate(REQUEST_ID, request_ID);
	}

	public Time getTime() {
		if (time == null) {
			time = new Time();
		}
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
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
			request.put(STOP, stop.getJSONObject());
			request.put(TIME, time.getTime());
			request.put(FLEX, flex);
			result.put(type, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
