package com.pmu.android.api.transport.impl;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class OfferMAction extends BaseAction implements ITransportCallBack {

	public static final String SUCCESS = "OfferMAction_Success";
	private Context context;
	private Trip trip;
	private String offer;

	public OfferMAction(Context newContext, String newOffer, Trip newTrip) {
		context = newContext;
		offer = newOffer;
		trip = newTrip;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		AsyncTransportCalls.offerTrip(u.getID(), u.getPin(), offer,
				trip.getID(), this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.OFFER_TRIP)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			try {
				Notify(SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
