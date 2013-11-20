package com.pmu.android.api.transport.impl;

import java.io.File;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class UploadFileAction extends BaseAction implements ITransportCallBack {

	private File file;
	private String trip;
	private String request;
	private String match;
	private Context context;
	private String flitId;

	public UploadFileAction(File newFile, String newTrip, Context newContext) {
		file = newFile;
		trip = newTrip;
		context = newContext;
	}

	public UploadFileAction(File newFile, String newRequest, String newMatch,
			Context newContext) {
		file = newFile;
		request = newRequest;
		match = newMatch;
		context = newContext;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		if (trip != null) {
			AsyncTransportCalls.getFlitT(u.getID(), u.getPin(), trip, this);
		} else {
			AsyncTransportCalls.getFlit(u.getID(), u.getPin(), request, match,
					this);
		}
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(AsyncTransportCalls.GET_FLIT)
				|| response.getCaller().equalsIgnoreCase(
						AsyncTransportCalls.GET_FLIT_T)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				JSONObject jo = new JSONObject(json);
				flitId = jo.getString("flitId");
				AsyncTransportCalls.uploadFile(file, flitId, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (response.getCaller().equalsIgnoreCase(flitId)) {
			Notify("UploadFileAction_Success");
		}
	}

}
