package com.pmu.android.api.transport.impl;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class SyncAction extends BaseAction implements ITransportCallBack {

	public static final String SUCCESS = "SyncAction_Success";
	public static final Object FAILURE = "SyncAction_Failure";
	private Context context;

	public SyncAction(Context newContext) {
		context = newContext;
	}

	@Override
	public String getName() {
		return SyncAction.class.getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		if (u.getGCMID() == null || u.getGCMID().length() < 0) {
			AsyncTransportCalls.registerGCM(context, this);
		} else {
			Notify(SUCCESS);
		}
	}

	@Override
	public void kill() {

	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(TransportContants.GCM_KEY)) {
			String regId = (String) response.getResponse();
			User u = ApiFactory.getObjectFactory(context).getUser();
			u.setGCMID(regId);
			AsyncTransportCalls.setNotificationId(u.getID(), u.getPin(),
					u.getGCMID(), this);
		} else if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.SET_NOTIFICATION_ID)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				JSONObject jo = new JSONObject(json);
				Notify(SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				Notify(FAILURE);
			}
		}
	}

}
