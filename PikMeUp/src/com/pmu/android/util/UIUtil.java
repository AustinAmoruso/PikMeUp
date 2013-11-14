package com.pmu.android.util;

import android.view.View;
import android.widget.EditText;

public class UIUtil {

	private static View view;

	public static void setView(View newView) {
		view = newView;
	}

	public static String getString(int id) {
		EditText edt = (EditText) view.findViewById(id);
		return edt.getText().toString();
	}

}
