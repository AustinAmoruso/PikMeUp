package com.pmu.android;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.obj.impl.User;
import com.pmu.android.api.storage.IStorage;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;
import com.pmu.android.api.transport.impl.GetTripDetailsAction;
import com.pmu.android.api.transport.impl.SyncAction;
import com.pmu.android.api.transport.impl.UploadFileAction;
import com.pmu.android.ui.impl.OfferDialog;

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
		loadFragment();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == OfferDialog.ACTION_TAKE_VIDEO) {
			if (resultCode == RESULT_OK) {
				// String path = data.getDataString();
				// path = path.replace("file://", "");
				// File f = new File(path);
				File f = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/flitUp.mp4");
				IStorage s = ApiFactory.getStrorageFactory(this)
						.getPreferences();
				if (s.getValue("trip") != null) {
					UploadFileAction ufa = new UploadFileAction(f,
							(String) s.getValue("trip"), this);
					s.setValue("trip", null);
					ufa.addCallback(this);
					ufa.performAction();
				} else {
					UploadFileAction ufa = new UploadFileAction(f,
							(String) s.getValue("request"),
							(String) s.getValue("match"), this);
					s.setValue("request", null);
					s.setValue("match", null);
					ufa.addCallback(this);
					ufa.performAction();
				}
				// Toast.makeText(this, "Video saved to:\n" + data.getData(),
				// Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Video recording cancelled.",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Failed to record video",
						Toast.LENGTH_LONG).show();
			}
		}
	}

}
