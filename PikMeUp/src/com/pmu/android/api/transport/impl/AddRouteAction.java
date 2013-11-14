package com.pmu.android.api.transport.impl;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class AddRouteAction extends BaseAction implements ITransportCallBack {

	public static final String ADD_ROUTE_ACTION_SUCCESS = "AddRouteAction_Success";
	private Context context;
	private Request request;

	public AddRouteAction(Context newContext, Request newRequest) {
		context = newContext;
		request = newRequest;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		if (validate(request)) {
			User u = ApiFactory.getObjectFactory(context).getUser();
			AsyncTransportCalls.setRequest(u.getID(), request.getJSONString(),
					this);
		}
	}

	private boolean validate(Request req) {
		return true;
	}

	@Override
	public void kill() {

	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.SET_REQUEST)) {
			Notify(ADD_ROUTE_ACTION_SUCCESS);
		}
	}

}
