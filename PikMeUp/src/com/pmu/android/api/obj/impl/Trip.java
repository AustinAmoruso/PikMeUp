package com.pmu.android.api.obj.impl;

import com.pmu.android.api.obj.IFeedObject;

public class Trip extends BaseAsyncObject implements IFeedObject {

	public static final String TRIP_ID = "request_ID";
	public static final String TYPE = "type";
	public static final String FLEX = "flex";
	public static final String TIME = "time";
	public static final String STOP = "stop";
	public static final String START = "start";
	public static final String DRIVE = "drive";
	public static final String RIDE = "ride";
	public static final String TRIP = "trip";
	private Request driver;
	private Request rider;
	private String tripID;
	private String type;
	private String status;
	private String offer;

	public Trip() {
		driver = new Request();
		rider = new Request();
	}

	public Request getDriver() {
		return driver;
	}

	public void setDriver(Request driver) {
		this.driver = driver;
	}

	public Request getRider() {
		return rider;
	}

	public void setRider(Request rider) {
		this.rider = rider;
	}

	public Location getStart() {
		return rider.getStart();
	}

	public void setStart(Location start) {
		rider.setStart(start);
		start.addOnActionCallback(this);
		onUpdate(START, start);
	}

	public Location getEnd() {
		return rider.getEnd();
	}

	public void setEnd(Location end) {
		rider.setEnd(end);
		end.addOnActionCallback(this);
		onUpdate(STOP, end);
	}

	public String getFlex() {
		return rider.getFlex();
	}

	public void setFlex(String flex) {
		rider.setFlex(flex);
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
		return tripID;
	}

	public void setID(String trip_ID) {
		this.tripID = trip_ID;
		onUpdate(TRIP_ID, trip_ID);
	}

	public Time getTime() {
		return rider.getTime();
	}

	public void setTime(Time time) {
		rider.setTime(time);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}



}
