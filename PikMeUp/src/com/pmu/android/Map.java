package com.pmu.android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.IAction;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;
import com.pmu.android.api.transport.impl.GetRequestsAction;
import com.pmu.android.api.transport.impl.SyncAction;
import com.pmu.android.api.transport.impl.TransportContants;
import com.pmu.android.ui.impl.FeedUI;
import com.pmu.android.util.SoapParser;

public class Map extends Fragment implements ITransportCallBack,
		IActionCallback {

	private static final int CODE = 110101;
	private MapView m;
	private ArrayList<FeedUI> feed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.map, container, false);
		m = (MapView) rootView.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.drives, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_route:
			Main m = (Main) getActivity();
			m.SwapFragmentByClass(RouteNew.class.getName());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMapToCurrentCords();
		IAction a = new SyncAction(getActivity());
		a.addCallback(this);
		a.performAction();
		loadRequests();
	}

	private void loadRequests() {
		GetRequestsAction gra = new GetRequestsAction(getActivity());
		gra.addCallback(this);
		gra.performAction();
	}

	private void setMapToCurrentCords() {
		HashMap<String, String> loc = ApiFactory.getAdapterFactory()
				.getLocation().query(getActivity());
		double lat = Double.valueOf(loc.get(Location.LAT));
		double lng = Double.valueOf(loc.get(Location.LONG));
		setMapCordinates(lat, lng, 13);
	}

	private void setMapCordinates(double lat, double lng, int zoom) {
		LatLng loc = new LatLng(lat, lng);
		GoogleMap gm = m.getMap();
		gm.clear();
		gm.setMyLocationEnabled(true);
		gm.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
	}

	@Override
	public void onPause() {
		super.onPause();
		m.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		m.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		m.onLowMemory();
	}

	public void pickImage(View View) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
			Uri selectedImageUri = data.getData();
			String path = getPath(selectedImageUri);
			File f = new File(path);
			AsyncTransportCalls.uploadFile(f, "thisisatest", this);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void refreshFeed() {
		LinearLayout llF = (LinearLayout) getView().findViewById(
				R.id.llRequests);
		llF.removeAllViews();
		for (IFeedObject ifo : ApiFactory.getObjectFactory(getActivity())
				.getRequests().getIFeedObjects()) {
			FeedUI fu = new FeedUI(ifo, getActivity());
			feed.add(fu);
			llF.addView(fu.getView());
		}
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase("thisisatest")) {
			try {
				UploadResult ur = (UploadResult) response.getResponse();
				Log.e("Bucket:", ur.getBucketName());
				Log.e("Tag:", ur.getETag());
				Log.e("Key:", ur.getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (response.getCaller().equalsIgnoreCase(
				TransportContants.GCM_KEY)) {
			String ri = (String) response.getResponse();
			ri.toString();
		} else if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.CREATE_USER)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			json.toCharArray();
		}
	}

	@Override
	public void onComplete(Object result) {
		if (result instanceof String) {
			String val = (String) result;
			if (val.equalsIgnoreCase(GetRequestsAction.SUCCESS)) {
				refreshFeed();
			}
		}
	}
}
