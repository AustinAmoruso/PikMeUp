package com.pmu.android.ui.impl;

import android.widget.AutoCompleteTextView;

import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Request;

public class FlexUI implements IObjectCallback {

	private AutoCompleteTextView actv;
	private Request requst;

	public FlexUI(AutoCompleteTextView newActv, Request newRequest) {
		actv = newActv;
		requst = newRequest;
		requst.addOnActionCallback(this);
	}

	public AutoCompleteTextView getActv() {
		return actv;
	}

	public void setActv(AutoCompleteTextView actv) {
		this.actv = actv;
	}

	@Override
	public void onAction(String action, Object value) {
		if (action.equalsIgnoreCase(Request.FLEX)) {
			actv.setText((String) value);
		}
	}
}
