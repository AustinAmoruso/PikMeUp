package com.pmu.android.api;

import android.content.Context;

import com.pmu.android.api.obj.ITrips;
import com.pmu.android.api.obj.impl.Trips;
import com.pmu.android.api.obj.impl.User;

public class ObjectFactory {

	private Context context;
	private ITrips trips;
	private User user;

	public ObjectFactory(Context c) {
		context = c;
	}

	public ITrips getTrips() {
		if (trips == null) {
			trips = new Trips();
		}
		return trips;
	}

	public User getUser() {
		if (user == null) {
			user = new User(context);
		}
		return user;
	}

}
