package com.pmu.android.api.transport.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.Requests;
import com.pmu.android.api.obj.impl.Time;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class GetMatchesAction extends BaseAction implements ITransportCallBack {

	public static final String SUCCESS = "GetMatchesAction_Success";
	public static final String FAILURE = "GetMatchesAction_Failure";
	private Request request;
	private Context context;
	private Requests requests;
	private ProgressDialog pd;

	public GetMatchesAction(Request newRequest, Context newContext,
			Requests newRequests) {
		request = newRequest;
		context = newContext;
		requests = newRequests;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		pd = ProgressDialog.show(context, "Retrieving Matches",
				"Please wait while the server is being contracted");
		User u = ApiFactory.getObjectFactory(context).getUser();
		AsyncTransportCalls.getMatches(u.getID(), request.getID(), this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.GET_MATCHES)) {
			pd.dismiss();
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				JSONObject jo = new JSONObject(json);
				if (jo.has("requests")) {
					JSONArray ja = jo.getJSONArray("requests");
					requests.Clear();
					for (int i = 0; i < ja.length(); i++) {
						JSONObject rJO = ja.getJSONObject(i);
						IFeedObject ifo = null;
						JSONObject inner = null;
						if (rJO.has("trip")) {
							ifo = new Trip();
							inner = rJO.getJSONObject("trip");
							ifo.setType(inner.getString("type"));
						} else if (rJO.has("drive")) {
							ifo = new Request();
							inner = rJO.getJSONObject("drive");
							ifo.setType("drive");
						} else if (rJO.has("ride")) {
							ifo = new Request();
							inner = rJO.getJSONObject("ride");
							ifo.setType("ride");
						}
						JSONObject start = inner.getJSONObject("start");
						ifo.setStart(new Location(start.getString("alias"),
								start.getString("long"), start.getString("lat")));
						JSONObject stop = inner.getJSONObject("stop");
						ifo.setEnd(new Location(stop.getString("alias"), stop
								.getString("long"), stop.getString("lat")));
						Time t = new Time();
						t.parse(inner.getString("time"));
						ifo.setTime(t);
						ifo.setFlex(inner.getString("flex"));
						ifo.setID(inner.getString("id"));
						requests.add(ifo);
					}
					Notify(SUCCESS);
				} else {
					Notify(FAILURE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
