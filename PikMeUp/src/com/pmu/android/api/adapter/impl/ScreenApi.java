package com.pmu.android.api.adapter.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.pmu.android.api.adapter.IScreenApi;

public class ScreenApi implements IScreenApi {

	private Context context;

	public ScreenApi(Context newContext) {
		context = newContext;
	}

	public void lockScreen(Activity a) {
		a.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public void unlockScreen(Activity a) {
		a.getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public void showKeyboard() {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	public void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public void enterFullScreen(Activity a) {
		a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void exitFullScreen(Activity a) {
		a.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	@Override
	public Point getScreenSize() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}
}
