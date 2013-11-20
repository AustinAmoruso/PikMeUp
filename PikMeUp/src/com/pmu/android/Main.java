package com.pmu.android;

import java.lang.reflect.Constructor;
import java.util.Scanner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;
import com.pmu.android.api.transport.impl.GetTripDetailsAction;
import com.pmu.android.api.transport.impl.SyncAction;

public class Main extends Activity implements IActionCallback {

	Trip trip;

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.main);
		Bundle i = getIntent().getExtras();
		// if (i != null) {
		// String tripId = i.getString("tripId");
		// trip = new Trip();
		// trip.setID(tripId);
		// GetTripDetailsAction gtda = new GetTripDetailsAction(this, trip);
		// gtda.addCallback(this);
		// gtda.performAction();
		// } else {
		loadFragment();
		// }
	}

	@Override
	protected void onResume() {
		super.onResume();
		AsyncTransportCalls.enableQueue(this);
	}

	private void loadFragment() {
		User u = ApiFactory.getObjectFactory(this).getUser();
		if (u.getID() != null && u.getID().length() > 0) {
			SwapFragmentByClass(Map.class.getName());
		} else {
			SwapFragmentByClass(Create.class.getName());
		}
	}

	private void SwapFragment(Fragment frag, String name) {
		FragmentManager fm = getFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1)
					.getName().equalsIgnoreCase(name)
					&& !(fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1)
							.getName()
							.equalsIgnoreCase(Scanner.class.getName()))) {
				return;
			}
		}
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contentContainer, frag, name);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(name);
		ft.commit();
	}

	private Fragment CreateFragment(String name) {
		Fragment frag = null;
		try {
			Class cls = Class.forName(name);
			Constructor contstructor = cls.getConstructor();
			frag = (Fragment) contstructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frag;
	}

	public void SwapFragmentByClass(String name) {
		Fragment frag = CreateFragment(name);
		if (frag != null) {
			SwapFragment(frag, name);
		}
	}

	@Override
	public void onComplete(Object result) {
		if (result instanceof String) {
			String val = (String) result;
			if (val.equalsIgnoreCase(SyncAction.SUCCESS)) {

			} else if (val.equalsIgnoreCase(GetTripDetailsAction.SUCCESS)) {

			}
		}
	}

	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		if (fm.getBackStackEntryCount() <= 1) {
			if (ApiFactory.getObjectFactory(this).getRequests().getSelected() != null
					&& fm.findFragmentByTag(Map.class.getName()) != null) {
				ApiFactory.getObjectFactory(this).getRequests()
						.setSelected(null);
			} else {
				finish();
			}
		} else {
			super.onBackPressed();
		}
	}

}
