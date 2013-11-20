package com.pmu.android.api.transport.impl;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.Time;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class GetTripDetailsAction extends BaseAction implements
		ITransportCallBack {

	public static final String SUCCESS = "GetTripDetailsAction_Success";
	public static final String FAILURE = "GetTripDetailsAction_Failure";
	private Context context;
	private Trip trip;
	private ProgressDialog pd;

	public GetTripDetailsAction(Context newContext, Trip newTrip) {
		context = newContext;
		trip = newTrip;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		pd = ProgressDialog.show(context, "Retrieving Trip",
				"Please wait as the server is being contacted.");
		AsyncTransportCalls.getTripDetails(u.getID(), u.getPin(), trip.getID(),
				this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.GET_TRIP_DETAILS)) {
			pd.dismiss();
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				JSONObject jo = new JSONObject(json);
				JSONObject t = jo.getJSONObject("trip");
				JSONObject drive = t.getJSONObject("drive");
				JSONObject ride = t.getJSONObject("ride");
				if (ride.has("status")) {
					trip.setStatus(ride.getString("status"));
				}
				if (t.has("offer")) {
					trip.setOffer(t.getString("offer"));
				}
				trip.setDriver(parseRequest(drive, "drive"));
				trip.setRider(parseRequest(ride, "ride"));
				Notify(SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Request parseRequest(JSONObject jo, String type) {
		Request result = new Request();
		try {
			result.setType(type);
			result.setID(jo.getString("id"));
			Time t = new Time();
			t.parse(jo.getString("time"));
			result.setTime(t);
			result.setFlex(jo.getString("flex"));
			JSONObject sJO = jo.getJSONObject("start");
			Location startL = new Location(sJO.getString("alias"),
					sJO.getString("long"), sJO.getString("lat"));
			JSONObject eJO = jo.getJSONObject("stop");
			Location endL = new Location(eJO.getString("alias"),
					eJO.getString("long"), eJO.getString("lat"));
			result.setStart(startL);
			result.setEnd(endL);
		} catch (Exception e) {

		}
		return result;
	}

}
