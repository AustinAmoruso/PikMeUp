package com.pmu.android.api.transport.impl;

import java.io.File;
import java.util.LinkedHashMap;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;

public class AsyncTransportCalls {

	public static final String GET_LOCATIONS = "getLocations";
	public static final String SET_NOTIFICATION_ID = "setNotificationId";
	public static final String SET_TRIP_STATUS = "setTripStatus";
	public static final String OFFER_RESPONSE = "offerResponse";
	public static final String OFFER = "offer";
	public static final String GET_MATCHES = "getMatches";
	public static final String GET_ACCOUNT = "getAccount";
	public static final String SET_ACCOUNT = "setAccount";
	public static final String DELETE_REQUEST = "deleteRequest";
	public static final String EDIT_REQUEST = "editRequest";
	public static final String SET_REQUEST = "setRequest";
	public static final String GET_REQUESTS = "getRequests";
	public static final String ADD_LOCATION = "addLocation";
	public static final String CHANGE_PIN = "changePin";
	public static final String LOGIN = "login";
	public static final String CREATE_USER = "createUser";
	public static boolean queue = false;
	public static Context context;

	private static void CallMethod(String method,
			LinkedHashMap<String, String> hmp, ITransportCallBack callback) {
		IAsyncTransportCommand command = new AsyncSoapCommand(getFullUrl(),
				method, getNameSpace(), hmp, callback);
		RunCommand(command);
	}

	private static String getFullUrl() {
		if (context != null) {
			if (ApiFactory.getStrorageFactory(context).getSettings()
					.getDemoMode()) {
				return TransportContants.DEMO_FULLURL;
			}
		}
		return TransportContants.FULLURL;
	}

	private static String getNameSpace() {
		if (context != null) {
			if (ApiFactory.getStrorageFactory(context).getSettings()
					.getDemoMode()) {
				return TransportContants.DEMO_NAMESPACE;
			}
		}
		return TransportContants.NAMESPACE;
	}

	private static void RunCommand(IAsyncTransportCommand command) {
		if (queue) {
			ApiFactory.getTransportFactory().getTransportQueque(context)
					.add(command);
		} else {
			AsyncTransportLayer atl = new AsyncTransportLayer();
			atl.execute(command);
		}
	}

	public static void enableQueue(Context newContext) {
		context = newContext;
		queue = true;
	}

	public static void disableQueue() {
		context = null;
		queue = false;
	}

	public static void getImage(String url, ITransportCallBack callback) {
		RunCommand(new AsyncImageCommand(url, callback));
	}

	public static void uploadFile(File file, String name,
			ITransportCallBack callback) {
		RunCommand(new AsyncFileUploadCommand(file, name, callback));
	}

	public static void registerGCM(Context c, ITransportCallBack callback) {
		RunCommand(new AsyncGCMCommand(callback, c, TransportContants.GCM_KEY));
	}

	public static void placeDetails(String referenceId, Location location,
			ITransportCallBack callback) {
		try {
			RunCommand(new PlaceDetailCommand(referenceId, location, callback));
		} catch (Exception e) {
			e.printStackTrace();
			callback.onCallback(new PlaceDetailResponse(null, referenceId));
		}
	}

	public static void createUser(String username, String pin,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("username", username);
		hmp.put("pin", pin);
		CallMethod(CREATE_USER, hmp, callback);
	}

	public static void login(String username, String pin,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("username", username);
		hmp.put("pin", pin);
		CallMethod(LOGIN, hmp, callback);
	}

	public static void changePin(String username, String deviceId,
			String pinOld, String pinNew, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("username", username);
		hmp.put("deviceId", deviceId);
		hmp.put("pinOld", pinOld);
		hmp.put("pinNew", pinNew);
		CallMethod(CHANGE_PIN, hmp, callback);
	}

	public static void addLocation(String deviceId, String pin, String alias,
			String location, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("alias", alias);
		hmp.put("location", location);
		CallMethod(ADD_LOCATION, hmp, callback);
	}

	public static void getLocations(String deviceId, String pin,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		CallMethod(GET_LOCATIONS, hmp, callback);
	}

	public static void setRequest(String deviceId, String info,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("info", info);
		CallMethod(SET_REQUEST, hmp, callback);
	}

	public static void getRequests(String deviceId, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		CallMethod(GET_REQUESTS, hmp, callback);
	}

	public static void editRequest(String deviceId, String info,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("info", info);
		CallMethod(EDIT_REQUEST, hmp, callback);
	}

	public static void deleteRequest(String deviceId, String requestId,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("requestId", requestId);
		CallMethod(DELETE_REQUEST, hmp, callback);
	}

	public static void setAccount(String deviceId, String pin, String info,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("info", info);
		CallMethod(SET_ACCOUNT, hmp, callback);
	}

	public static void getAccount(String deviceId, String pin,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		CallMethod(GET_ACCOUNT, hmp, callback);
	}

	public static void getMatches(String deviceId, String requestId,
			ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("requestId", requestId);
		CallMethod(GET_MATCHES, hmp, callback);
	}

	public static void offer(String deviceId, String pin, String requestId,
			String offer, String matchId, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("requestId", requestId);
		hmp.put("offer", offer);
		hmp.put("matchId", matchId);
		CallMethod(OFFER, hmp, callback);
	}

	public static void offerResponse(String deviceId, String pin,
			String tripId, String choice, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("tripId", tripId);
		hmp.put("choice", choice);
		CallMethod(OFFER_RESPONSE, hmp, callback);
	}

	public static void setTripStatus(String deviceId, String pin,
			String tripId, String status, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("tripId", tripId);
		hmp.put("status", status);
		CallMethod(SET_TRIP_STATUS, hmp, callback);
	}

	public static void setNotificationId(String deviceId, String pin,
			String regId, ITransportCallBack callback) {
		LinkedHashMap<String, String> hmp = new LinkedHashMap<String, String>();
		hmp.put("deviceId", deviceId);
		hmp.put("pin", pin);
		hmp.put("regId", regId);
		CallMethod(SET_NOTIFICATION_ID, hmp, callback);
	}

}