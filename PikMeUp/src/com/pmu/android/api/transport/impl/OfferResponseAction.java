package com.pmu.android.api.transport.impl;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class OfferResponseAction extends BaseAction implements
		ITransportCallBack {

	public static final String SUCCESS = "OfferResponseAction_Success";
	private Context context;
	private Trip trip;
	private String choice;

	public OfferResponseAction(Context c, Trip t, String ch) {
		context = c;
		trip = t;
		choice = ch;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		AsyncTransportCalls.offerResponse(u.getID(), u.getPin(), trip.getID(),
				choice, this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		Notify(SUCCESS);
	}

}
