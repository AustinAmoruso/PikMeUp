package com.pmu.android.ui;

import android.content.Context;
import android.view.View;

import com.pmu.android.api.obj.ITrip;

public interface ITripUI {

	View getView(Context context);

	ITrip getItem();

	View getView();

}