package com.pmu.android.api.adapter;

import android.hardware.Camera;
import android.hardware.Camera.Size;

public interface ICameraApi {

	Camera getCamera();

	void releaseCamera();

	void autofocus();

	void pauseCamera();

	void changeLight(boolean on);

	boolean isLightOn();

	void setPreviewSize(int width, int height);

	void autofocus(int autoFocusDelay);

	void setPreviewSize(int width, int height, int rotaion);

	Size getBestPreviewSize(int width, int height);

}
