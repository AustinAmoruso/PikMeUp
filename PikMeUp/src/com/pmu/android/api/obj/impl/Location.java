package com.pmu.android.api.obj.impl;

import org.json.JSONObject;

import com.pmu.android.api.storage.ISerialize;

public class Location implements ISerialize {

	private String longitude;
	private String latitude;
	private String alias;
	private String type;

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
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject result = new JSONObject();
		JSONObject request = new JSONObject();
		try {
			request.put("lat", latitude);
			request.put("long", longitude);
			request.put("alias", alias);
			result.put(type, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
