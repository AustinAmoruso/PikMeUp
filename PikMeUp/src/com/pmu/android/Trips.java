package com.pmu.android;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.obj.ITrip;
import com.pmu.android.api.obj.ITrips;
import com.pmu.android.api.obj.ITripsCallback;
import com.pmu.android.ui.ITripUI;

public class Trips extends Fragment implements ITripsCallback {

	private ArrayList<ITripUI> items;
	private LinearLayout ll;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tripsscollview, container,
				false);
		return rootView;
	}

	public void onResume() {
		super.onResume();
		// init();
	}

	private void init() {
		ll = (LinearLayout) getView().findViewById(R.id.llTrips);
		ll.removeAllViews();
		items = new ArrayList<ITripUI>();
		ITrips trips = ApiFactory.getObjectFactory(getActivity()).getTrips();
		trips.addOnActionCallback(this);
		for (ITrip i : trips.getItems()) {
			addItem(i);
		}
	}

	public void addItem(ITripUI item) {
		items.add(item);
		ll.addView(item.getView(getActivity()));
	}

	public void addItem(ITrip item) {
		addItem(getUIItem(item));
	}

	public void onPause() {
		// ITrips trips = ApiFactory.getObjectFactory(getActivity()).getTrips();
		// trips.removeOnActionCallBack(this);
		super.onPause();
	}

	private ITripUI getUIItem(ITrip i) {
		ITripUI result = null;
		// if (i instanceof RouteTrip) {
		// result = new RouteTripUI((RouteTrip) i);
		// } else if (i instanceof RideTrip) {
		// result = new RideTripUI((RideTrip) i);
		// }
		return result;
	}

	@Override
	public void onAction(String action, Object value) {
		if (action.equalsIgnoreCase(com.pmu.android.api.obj.impl.Trips.ADD)) {
			addItem((ITrip) value);
		} else if (action
				.equalsIgnoreCase(com.pmu.android.api.obj.impl.Trips.CLEAR)) {
			clearCart();
		} else if (action
				.equalsIgnoreCase(com.pmu.android.api.obj.impl.Trips.REMOVE)) {
			removeItem((ITrip) value);
		}
	}

	private void removeItem(ITrip item) {
		for (ITripUI icu : items) {
			if (icu.getItem().equals(item)) {
				ll.removeView(icu.getView());
			}
		}
	}

	private void clearCart() {
		items.clear();
		ll.removeAllViews();
	}

	@Override
	public String getName() {
		return getClass().getName();
	}
}
