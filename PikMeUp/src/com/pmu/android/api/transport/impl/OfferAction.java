package com.pmu.android.api.transport.impl;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.BaseAction;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.util.SoapParser;

public class OfferAction extends BaseAction implements ITransportCallBack {

	public static final String SUCCESS = "OfferAction_Success";
	private Context context;
	private Request request;
	private Request match;
	private String offer;

	public OfferAction(Context newContext, Request newRequest, String newOffer,
			Request newMatch) {
		context = newContext;
		request = newRequest;
		offer = newOffer;
		match = newMatch;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		User u = ApiFactory.getObjectFactory(context).getUser();
		AsyncTransportCalls.offer(u.getID(), u.getPin(), request.getID(),
				offer, match.getID(), this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(AsyncTransportCalls.OFFER)) {
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
