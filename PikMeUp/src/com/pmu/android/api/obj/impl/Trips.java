package com.pmu.android.api.obj.impl;

import java.util.Collection;

import com.pmu.android.api.obj.ITrip;
import com.pmu.android.api.obj.ITrips;
import com.pmu.android.api.obj.ITripsCallback;

public class Trips implements ITrips {
	public static final String REMOVE = "remove";
	public static final String ADD = "add";
	public static final String CLEAR = "clear";

	@Override
	public Collection<ITrip> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeItem(ITrip i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelectedItem(ITrip i) {
		// TODO Auto-generated method stub

	}

	@Override
	public ITrip getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOnActionCallback(ITripsCallback icb) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeOnActionCallBack(ITripsCallback icb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addItem(ITrip i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean allowCheckout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAllowCheckout(boolean allow) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean push() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetPush() {
		// TODO Auto-generated method stub

	}

}
