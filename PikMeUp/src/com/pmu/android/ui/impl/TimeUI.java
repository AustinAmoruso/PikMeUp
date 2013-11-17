package com.pmu.android.ui.impl;

import android.widget.AutoCompleteTextView;

import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Time;

public class TimeUI implements IObjectCallback {

	private AutoCompleteTextView actv;
	private Time time;

	public TimeUI(AutoCompleteTextView newActv, Time newTime) {
		actv = newActv;
		time = newTime;
		time.addOnActionCallback(this);
	}

	public AutoCompleteTextView getActv() {
		return actv;
	}

	public void setActv(AutoCompleteTextView actv) {
		this.actv = actv;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@Override
	public void onAction(String action, Object value) {
		if (action.equalsIgnoreCase(Time.TIME_DISPLAY)) {
			actv.setText(time.getTimeDisplay());
		}
	}

}
