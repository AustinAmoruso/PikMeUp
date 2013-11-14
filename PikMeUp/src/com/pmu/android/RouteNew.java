package com.pmu.android;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.pmu.android.api.IAction;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.transport.impl.AddRouteAction;
import com.pmu.android.ui.impl.DatePickerFragment;
import com.pmu.android.ui.impl.FlexPickerFragment;
import com.pmu.android.ui.impl.LocationUI;
import com.pmu.android.ui.impl.MapPickerFragment;
import com.pmu.android.ui.impl.TimePickerFragment;

public class RouteNew extends Fragment implements OnClickListener,
		IActionCallback {

	private Request request;
	private LocationUI startUI;
	private LocationUI endUI;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.route, container, false);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		initObjects();
		initViews();
	}

	private void initObjects() {
		request = new Request();
		request.setStart(new Location(""));
		request.setEnd(new Location(""));
	}

	public void initViews() {
		ImageView ivStart = (ImageView) getView().findViewById(R.id.imgStart);
		ivStart.setOnClickListener(this);
		ImageView ivEnd = (ImageView) getView().findViewById(R.id.imgEnd);
		ivEnd.setOnClickListener(this);
		ImageView ivDate = (ImageView) getView().findViewById(R.id.imgDate);
		ivDate.setOnClickListener(this);
		ImageView ivTime = (ImageView) getView().findViewById(R.id.imgTime);
		ivTime.setOnClickListener(this);
		ImageView ivFlex = (ImageView) getView().findViewById(R.id.imgFlex);
		ivFlex.setOnClickListener(this);
		Button btnRoute = (Button) getView().findViewById(R.id.btnRoute);
		btnRoute.setOnClickListener(this);
		startUI = new LocationUI((AutoCompleteTextView) getView().findViewById(
				R.id.edtStart), request.getStart());
		endUI = new LocationUI((AutoCompleteTextView) getView().findViewById(
				R.id.edtEnd), request.getEnd());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imgStart) {
			MapPickerFragment mpf = new MapPickerFragment();
			mpf.setLocation(startUI.getLocation());
			mpf.show(getFragmentManager(), "mapPicker");
		} else if (v.getId() == R.id.imgEnd) {
			MapPickerFragment mpf = new MapPickerFragment();
			mpf.setLocation(endUI.getLocation());
			mpf.show(getFragmentManager(), "mapPicker");
		} else if (v.getId() == R.id.imgTime) {
			DialogFragment newFragment = new TimePickerFragment();
			newFragment.show(getFragmentManager(), "timePicker");
		} else if (v.getId() == R.id.imgDate) {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getFragmentManager(), "datePicker");
		} else if (v.getId() == R.id.imgFlex) {
			DialogFragment newFragment = new FlexPickerFragment();
			newFragment.show(getFragmentManager(), "flexPicker");
		} else if (v.getId() == R.id.btnRoute) {
			Request request = new Request();
			request.setEnd(new Location(getEditString(R.id.edtEnd)));
			request.setFlex(getEditString(R.id.edtFlex));
			request.setStart(new Location(getEditString(R.id.edtStart)));
			request.setTime(getEditString(R.id.edtTime));
			request.setType(getRouteType());
			IAction a = new AddRouteAction(getActivity(), request);
			a.addCallback(this);
			a.performAction();
		}
	}

	private String getEditString(int id) {
		EditText edt = (EditText) getView().findViewById(id);
		return edt.getText().toString();
	}

	private boolean getChecked(int id) {
		Switch s = (Switch) getView().findViewById(id);
		return s.isChecked();
	}

	private String getRouteType() {
		if (getChecked(R.id.switchType)) {
			return "ride";
		} else {
			return "drive";
		}
	}

	@Override
	public void onComplete(Object result) {
		Drawer d = (Drawer) getActivity();
		d.SwapFragmentByClass(Map.class.getName());
	}
}
