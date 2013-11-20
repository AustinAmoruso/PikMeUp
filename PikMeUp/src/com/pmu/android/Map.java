package com.pmu.android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.IAction;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.Requests;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;
import com.pmu.android.api.transport.impl.GetMatchesAction;
import com.pmu.android.api.transport.impl.GetRequestsAction;
import com.pmu.android.api.transport.impl.GetTripDetailsAction;
import com.pmu.android.api.transport.impl.SyncAction;
import com.pmu.android.api.transport.impl.TransportContants;
import com.pmu.android.ui.impl.FeedUI;
import com.pmu.android.util.SoapParser;

public class Map extends Fragment implements ITransportCallBack,
		IActionCallback, IObjectCallback {

	private static final int CODE = 110101;
	private MapView m;
	private GoogleMap gm;
	private ArrayList<FeedUI> feed;
	private Requests viewOnly;
	private TextView tvOverlay;
	private Trip tripHolder;
	private CircleOptions co;

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
		getActivity().getActionBar().show();
		m.onResume();
		viewOnly = new Requests();
		tvOverlay = (TextView) getView().findViewById(R.id.txtOverlay);
		tvOverlay.setText("");
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gm = m.getMap();
		setMapToCurrentCords();
		IAction a = new SyncAction(getActivity());
		a.addCallback(this);
		a.performAction();
		loadRequests();
	}

	private void loadRequests() {
		ApiFactory.getObjectFactory(getActivity()).getRequests()
				.addOnActionCallback(this);
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
		resetMap();
		gm.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
	}

	public void resetMap() {
		gm.clear();
		gm.setMyLocationEnabled(true);
		UiSettings s = gm.getUiSettings();
		// s.setAllGesturesEnabled(false);
		s.setCompassEnabled(false);
		s.setMyLocationButtonEnabled(false);
		s.setScrollGesturesEnabled(true);
		s.setTiltGesturesEnabled(false);
		s.setZoomControlsEnabled(false);
		s.setZoomGesturesEnabled(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		m.onPause();
		ApiFactory.getObjectFactory(getActivity()).getRequests()
				.removeOnActionCallBack(this);
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
		feed = new ArrayList<FeedUI>();
		for (IFeedObject ifo : ApiFactory.getObjectFactory(getActivity())
				.getRequests().getIFeedObjects()) {
			FeedUI fu = new FeedUI(ifo, getActivity());
			feed.add(fu);
			llF.addView(fu.getView());
		}
		if (feed.size() == 0) {
			llF.addView(getEmptyTextview());
		}
	}

	private void refreshMatches() {
		LinearLayout llF = (LinearLayout) getView().findViewById(
				R.id.llRequests);
		llF.removeAllViews();
		feed = new ArrayList<FeedUI>();
		for (IFeedObject ifo : viewOnly.getIFeedObjects()) {
			FeedUI fu = new FeedUI(ifo, getActivity());
			feed.add(fu);
			llF.addView(fu.getView());
		}
		if (feed.size() == 0) {
			llF.addView(getEmptyTextview());
		}
	}

	private TextView getEmptyTextview() {
		TextView result = new TextView(getActivity());
		if (viewOnly == null) {
			result.setText("No Requests");
		} else {
			result.setText("No Matches");
		}
		result.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Large);
		result.setGravity(Gravity.CENTER);
		result.setPadding(10, 10, 10, 10);
		return result;
	}

	private void loadMatches(Request r) {
		GetMatchesAction gma = new GetMatchesAction(r, getActivity(), viewOnly);
		gma.addCallback(this);
		gma.performAction();
		gm.clear();
		tvOverlay.setText(r.getType().toUpperCase() + " "
				+ r.getTime().toString());
		displayRequest(r, gm);
	}

	private void refreshTrip() {
		drawTripOnMap();
		viewOnly.Clear();
		viewOnly.add(tripHolder.getDriver());
		viewOnly.add(tripHolder.getRider());
		LinearLayout llF = (LinearLayout) getView().findViewById(
				R.id.llRequests);
		llF.removeAllViews();
		feed = new ArrayList<FeedUI>();
		for (IFeedObject ifo : viewOnly.getIFeedObjects()) {
			FeedUI fu = new FeedUI(ifo, getActivity());
			fu.setClickable(false);
			feed.add(fu);
			llF.addView(fu.getView());
		}
		if (feed.size() == 0) {
			llF.addView(getEmptyTextview());
		}
	}

	private void drawTripOnMap() {
		gm.clear();
		if (tripHolder != null) {
			Request primary;
			Request secondary;
			if (tripHolder.getType().equalsIgnoreCase("drive")) {
				primary = tripHolder.getDriver();
				secondary = tripHolder.getRider();
			} else {
				primary = tripHolder.getRider();
				secondary = tripHolder.getDriver();
			}
			if (primary != null && secondary != null) {
				displayRequest(primary, gm);
				displayMatch(secondary, gm);
				ArrayList<Request> rs = new ArrayList<Request>();
				rs.add(primary);
				rs.add(secondary);
				MoveCamera(rs);
			}
		}

	}

	private void loadTrip(Trip t) {
		tripHolder = t;
		gm.clear();
		GetTripDetailsAction gtda = new GetTripDetailsAction(getActivity(), t);
		gtda.addCallback(this);
		gtda.performAction();
	}

	private void displayRequest(Request r, GoogleMap gm) {
		LatLng sLatLng = new LatLng(Double.valueOf(r.getStart().getLatitude()),
				Double.valueOf(r.getStart().getLongitude()));
		gm.addMarker(new MarkerOptions()
				.title("Start")
				.snippet(r.getStart().getAlias())
				.position(sLatLng)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		LatLng eLatLng = new LatLng(Double.valueOf(r.getEnd().getLatitude()),
				Double.valueOf(r.getEnd().getLongitude()));
		gm.addMarker(new MarkerOptions()
				.title("Stop")
				.snippet(r.getEnd().getAlias())
				.position(eLatLng)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		gm.addPolyline(new PolylineOptions().add(sLatLng).add(eLatLng));
		ArrayList<Request> rs = new ArrayList<Request>();
		rs.add(r);
		MoveCamera(rs);
	}

	private void displayMatch(Request r, GoogleMap gm) {
		LatLng sLatLng = new LatLng(Double.valueOf(r.getStart().getLatitude()),
				Double.valueOf(r.getStart().getLongitude()));
		gm.addMarker(new MarkerOptions()
				.title("Start")
				.snippet(r.getStart().getAlias())
				.position(sLatLng)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
		LatLng eLatLng = new LatLng(Double.valueOf(r.getEnd().getLatitude()),
				Double.valueOf(r.getEnd().getLongitude()));
		gm.addMarker(new MarkerOptions()
				.title("Stop")
				.snippet(r.getEnd().getAlias())
				.position(eLatLng)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
		gm.addPolyline(new PolylineOptions().add(sLatLng).add(eLatLng)
				.color(R.color.grey));
		LatLngBounds llb = LatLngBounds.builder().include(sLatLng)
				.include(eLatLng).build();
		// gm.animateCamera(CameraUpdateFactory.newLatLngBounds(llb, 100));
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
			} else if (val.equalsIgnoreCase(GetMatchesAction.SUCCESS)) {
				refreshMatches();
			} else if (val.equalsIgnoreCase(GetTripDetailsAction.SUCCESS)) {
				refreshTrip();
			}
		}
	}

	@Override
	public void onAction(String action, Object value) {
		if (action.equalsIgnoreCase(Requests.SELECTED)) {
			if (value != null) {
				if (value instanceof Request) {
					Request r = (Request) value;
					loadMatches(r);
				} else if (value instanceof Trip) {
					Trip t = (Trip) value;
					loadTrip(t);
				}
			} else {
				viewOnly = new Requests();
				tvOverlay = (TextView) getView().findViewById(R.id.txtOverlay);
				tvOverlay.setText("");
				setMapToCurrentCords();
				IAction a = new SyncAction(getActivity());
				a.addCallback(this);
				a.performAction();
				loadRequests();
			}
		} else if (action.equalsIgnoreCase(FeedUI.MATCH)) {
			gm.clear();
			Request r = (Request) ApiFactory.getObjectFactory(getActivity())
					.getRequests().getSelected();
			displayRequest(r, gm);
			Request m = (Request) value;
			displayMatch(m, gm);
			ArrayList<Request> rs = new ArrayList<Request>();
			rs.add(r);
			rs.add(m);
			MoveCamera(rs);
		}
	}

	private void MoveCamera(ArrayList<Request> reqs) {
		Builder b = LatLngBounds.builder();
		for (Request r : reqs) {
			LatLng start = new LatLng(
					Double.valueOf(r.getStart().getLatitude()),
					Double.valueOf(r.getStart().getLongitude()));
			LatLng stop = new LatLng(Double.valueOf(r.getEnd().getLatitude()),
					Double.valueOf(r.getEnd().getLongitude()));
			b.include(start);
			b.include(stop);
		}
		LatLngBounds llb = b.build();
		if (llb != null) {
			gm.moveCamera(CameraUpdateFactory.newLatLngBounds(llb, 100));
			// drawTint(llb);
		}
	}

	private void drawTint(LatLngBounds llb) {

		// LatLng ne = new LatLng(llb.northeast.latitude + 1,
		// llb.northeast.longitude + 1);
		// LatLng sw = new LatLng(llb.southwest.latitude - 1,
		// llb.southwest.longitude - 1);
		// LatLng nw = new LatLng(sw.latitude - 1, ne.longitude + 1);
		// LatLng se = new LatLng(ne.latitude + 1, sw.longitude - 1);
		// PolygonOptions rectOptions = new PolygonOptions()
		// .add(nw, ne, se, sw, nw).strokeColor(Color.WHITE)
		// .fillColor(Color.argb(76, 0, 0, 0));
		if (co == null) {
			co = new CircleOptions().center(llb.getCenter()).radius(40074000)
					.strokeColor(Color.TRANSPARENT)
					.fillColor(Color.argb(76, 0, 0, 0));
			gm.addCircle(co);
		} else {
			co = new CircleOptions().center(llb.getCenter()).radius(40074000)
					.strokeColor(Color.TRANSPARENT)
					.fillColor(Color.argb(76, 0, 0, 0));
			gm.addCircle(co);
		}
	}
}
