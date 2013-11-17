package com.pmu.android.api.transport.impl;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class CreateUserAction extends BaseAction implements ITransportCallBack {

	public static final String FAILURE = "CreateUserAction_Failure";
	public static final String SUCCESS = "CreateUserAction_Success";
	private String username;
	private String password;
	private Context context;

	public CreateUserAction(String newUsername, String newPassword,
			Context newContext) {
		username = newUsername;
		password = newPassword;
		context = newContext;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		if (validate(username, password)) {
			AsyncTransportCalls.createUser(username, password, this);
		}
	}

	private boolean validate(String username, String password) {
		return true;
	}

	@Override
	public void kill() {

	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.CREATE_USER)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				JSONObject jo = new JSONObject(json);
				if (jo.has("id")) {
					String id = jo.getString("id");
					ApiFactory.getObjectFactory(context).getUser().setID(id);
					ApiFactory.getObjectFactory(context).getUser()
							.setPin(password);
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
