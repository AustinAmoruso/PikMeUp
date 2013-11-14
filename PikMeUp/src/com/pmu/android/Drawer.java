package com.pmu.android;

import java.lang.reflect.Constructor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;

import com.pmu.android.api.transport.impl.AsyncTransportCalls;

public class Drawer extends Activity implements DrawerListener {

	private DrawerLayout drawer;

	@Override
	public void onResume() {
		super.onResume();
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.setDrawerListener(this);
		AsyncTransportCalls.enableQueue(this);
		loadCenter();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer);
		loadLeft();
		loadRight();
		loadCenter();
	}

	private void loadRight() {
		loadTrips();
	}

	private void loadLeft() {
		loadMenu();
	}

	private void loadCenter() {
		SwapFragmentByClass(Create.class.getName());
	}

	private void loadTrips() {
		getFragmentManager().beginTransaction()
				.add(R.id.rltripscontent, new Trips()).commit();
	}

	private void loadMenu() {
		getFragmentManager().beginTransaction()
				.add(R.id.rlmenucontent, new Menu()).commit();
	}

	private void SwapFragment(Fragment frag, String name) {
		FragmentManager fm = getFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1)
					.getName().equalsIgnoreCase(name)) {
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

	public void closeMenu() {
		drawer.closeDrawers();
	}

	private void toggleMenu() {
		openMenu(!isMenuOpen());
	}

	public void openMenu(boolean open) {
		if (open) {
			drawer.closeDrawers();
			drawer.openDrawer(Gravity.START);
		} else {
			closeMenu();
		}
	}

	public void openBag(boolean open) {
		if (open) {
			drawer.closeDrawers();
			drawer.openDrawer(Gravity.END);
		} else {
			closeMenu();
		}
	}

	public void toggleBag() {
		openBag(!isBagOpen());
	}

	public boolean isBagOpen() {
		return drawer.isDrawerOpen(Gravity.END);
	}

	public boolean isMenuOpen() {
		return drawer.isDrawerOpen(Gravity.START);
	}

	public boolean isClosed() {
		return (!(isMenuOpen() || isBagOpen()));
	}

	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

}
