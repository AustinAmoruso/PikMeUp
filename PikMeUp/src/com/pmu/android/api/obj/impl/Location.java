package com.pmu.android.api.obj.impl;

import org.json.JSONObject;

import com.pmu.android.api.storage.ISerialize;

public class Location extends BaseAsyncObject implements ISerialize {

	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String REQUEST_ID = "requestID";
	public static final String TYPE = "type";
	public static final String ALIAS = "alias";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	private String longitude;
	private String latitude;
	private String alias;
	private String type;
	private String requestID;

	public Location(String newAlias) {
		alias = newAlias;
	}

	public Location(String newLongitude, String newLatitude) {
		longitude = newLongitude;
		latitude = newLatitude;
	}

	public Location(String newAlias, String newLongitude, String newLatitude) {
		alias = newAlias;
		longitude = newLongitude;
		latitude = newLatitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
		onUpdate(LONGITUDE, longitude);
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
		onUpdate(LATITUDE, latitude);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
		onUpdate(ALIAS, alias);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		onUpdate(TYPE, type);
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
		onUpdate(REQUEST_ID, requestID);
	}

	@Override
	public String getJSONString() {
		return getJSONObject().toString();
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject request = new JSONObject();
		try {
			request.put(LAT, latitude);
			request.put(LONG, longitude);
			request.put(ALIAS, alias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public String toString() {
		return alias;
	}
}
