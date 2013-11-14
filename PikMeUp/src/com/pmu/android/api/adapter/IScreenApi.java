package com.pmu.android.api.adapter;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

public interface IScreenApi {

	void lockScreen(Activity a);

	void unlockScreen(Activity a);

	void showKeyboard();

	void hideKeyboard(View view);

	void enterFullScreen(Activity a);

	void exitFullScreen(Activity a);

	Point getScreenSize();

}
