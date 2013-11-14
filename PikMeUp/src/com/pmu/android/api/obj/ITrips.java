package com.pmu.android.api.obj;

import java.util.Collection;

public interface ITrips {
	Collection<ITrip> getItems();

	boolean removeItem(ITrip i);

	void setSelectedItem(ITrip i);

	ITrip getSelectedItem();

	double getPrice();

	void Clear();

	void addOnActionCallback(ITripsCallback icb);

	boolean removeOnActionCallBack(ITripsCallback icb);

	boolean addItem(ITrip i);

	boolean ready();

	boolean allowCheckout();

	void setAllowCheckout(boolean allow);

	boolean push();

	int getItemCount();

	void resetPush();

}