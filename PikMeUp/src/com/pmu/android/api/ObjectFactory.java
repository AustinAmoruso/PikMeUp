package com.pmu.android.api;

import android.content.Context;

import com.pmu.android.api.obj.impl.Requests;
import com.pmu.android.api.obj.impl.User;

public class ObjectFactory {

	private Context context;
	private Requests requests;
	private User user;

	public ObjectFactory(Context c) {
		context = c;
	}

	public Requests getRequests() {
		if (requests == null) {
			requests = new Requests();
		}
		return requests;
	}

	public User getUser() {
		if (user == null) {
			user = new User(context);
		}
		return user;
	}

}
