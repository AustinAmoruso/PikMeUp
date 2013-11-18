package com.pmu.android;

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
import com.pmu.android.api.obj.impl.Time;
import com.pmu.android.api.transport.impl.AddRouteAction;
import com.pmu.android.ui.impl.DatePickerFragment;
import com.pmu.android.ui.impl.DateUI;
import com.pmu.android.ui.impl.FlexPickerFragment;
import com.pmu.android.ui.impl.FlexUI;
import com.pmu.android.ui.impl.LocationUI;
import com.pmu.android.ui.impl.MapPickerFragment;
import com.pmu.android.ui.impl.TimePickerFragment;
import com.pmu.android.ui.impl.TimeUI;

public class RouteNew extends Fragment implements OnClickListener,
		IActionCallback {

	private Request request;
	private LocationUI startUI;
	private LocationUI endUI;
	private TimeUI timeUI;
	private DateUI dateUI;
	private FlexUI flexUI;

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
		request.getStart().setType("start");
		request.setEnd(new Location(""));
		request.getEnd().setType("end");
		request.setTime(new Time());
	}

	public void initViews() {
		ImageView ivStart = (ImageView) getView().findViewById(R.id.imgStart);
		ivStart.setOnClickListener(this);
		AutoCompleteTextView atvStart = (AutoCompleteTextView) getView()
				.findViewById(R.id.edtStart);
		atvStart.setOnClickListener(this);
		ImageView ivEnd = (ImageView) getView().findViewById(R.id.imgEnd);
		ivEnd.setOnClickListener(this);
		AutoCompleteTextView atvEnd = (AutoCompleteTextView) getView()
				.findViewById(R.id.edtEnd);
		atvEnd.setOnClickListener(this);
		ImageView ivDate = (ImageView) getView().findViewById(R.id.imgDate);
		ivDate.setOnClickListener(this);
		AutoCompleteTextView atvDate = (AutoCompleteTextView) getView()
				.findViewById(R.id.edtDate);
		atvDate.setOnClickListener(this);
		ImageView ivTime = (ImageView) getView().findViewById(R.id.imgTime);
		ivTime.setOnClickListener(this);
		AutoCompleteTextView atvTime = (AutoCompleteTextView) getView()
				.findViewById(R.id.edtTime);
		atvTime.setOnClickListener(this);
		ImageView ivFlex = (ImageView) getView().findViewById(R.id.imgFlex);
		ivFlex.setOnClickListener(this);
		AutoCompleteTextView atvFlex = (AutoCompleteTextView) getView()
				.findViewById(R.id.edtFlex);
		atvFlex.setOnClickListener(this);
		Button btnRoute = (Button) getView().findViewById(R.id.btnRoute);
		btnRoute.setOnClickListener(this);
		startUI = new LocationUI((AutoCompleteTextView) getView().findViewById(
				R.id.edtStart), request.getStart());
		endUI = new LocationUI((AutoCompleteTextView) getView().findViewById(
				R.id.edtEnd), request.getEnd());
		timeUI = new TimeUI(atvTime, request.getTime());
		dateUI = new DateUI(atvDate, request.getTime());
		flexUI = new FlexUI(atvFlex, request);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imgStart || v.getId() == R.id.edtStart) {
			MapPickerFragment mpf = new MapPickerFragment();
			mpf.setLocation(startUI.getLocation());
			mpf.show(getFragmentManager(), "mapPicker");
		} else if (v.getId() == R.id.imgEnd || v.getId() == R.id.edtEnd) {
			MapPickerFragment mpf = new MapPickerFragment();
			mpf.setLocation(endUI.getLocation());
			mpf.show(getFragmentManager(), "mapPicker");
		} else if (v.getId() == R.id.imgTime || v.getId() == R.id.edtTime) {
			TimePickerFragment tpf = new TimePickerFragment();
			tpf.setTime(request.getTime());
			tpf.show(getFragmentManager(), "timePicker");
		} else if (v.getId() == R.id.imgDate || v.getId() == R.id.edtDate) {
			DatePickerFragment dpf = new DatePickerFragment();
			dpf.setTime(request.getTime());
			dpf.show(getFragmentManager(), "datePicker");
		} else if (v.getId() == R.id.imgFlex || v.getId() == R.id.edtFlex) {
			FlexPickerFragment fpf = new FlexPickerFragment();
			fpf.setRequest(request);
			fpf.show(getFragmentManager(), "flexPicker");
		} else if (v.getId() == R.id.btnRoute) {
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
		Main d = (Main) getActivity();
		d.SwapFragmentByClass(Map.class.getName());
	}
}
