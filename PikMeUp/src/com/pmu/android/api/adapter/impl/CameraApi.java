package com.pmu.android.api.adapter.impl;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Handler;

import com.pmu.android.api.adapter.ICameraApi;

public class CameraApi implements ICameraApi, AutoFocusCallback, Runnable {

	private Camera c;
	private int autoFD;
	private Handler handel;

	public CameraApi() {
		autoFD = 1;
	}

	public Camera getCamera() {
		if (c == null) {
			try {
				c = Camera.open(0);
			} catch (Exception e) {
				e.printStackTrace();
				c = Camera.open(0);
			} finally {
			}
		}
		return c;
	}

	public void releaseCamera() {
		if (c != null) {
			try {
				c.cancelAutoFocus();
			} catch (Exception e) {
			}
			try {
				c.stopPreview();
			} catch (Exception e) {
			}
			try {
				c.setPreviewCallback(null);
			} catch (Exception e) {
			}
			try {
				c.release();
			} catch (Exception e) {
			}
			try {
				c = null;
			} catch (Exception e) {
			}
		}
	}

	public void pauseCamera() {
		if (c != null) {
			handel.removeCallbacks(this);
			c.cancelAutoFocus();
			c.stopPreview();
			c.setPreviewCallback(null);
			c.setPreviewCallback(null);
			// c.unlock();
		}
	}

	@Override
	public void autofocus(int autoFocusDelay) {
		autoFD = autoFocusDelay;
		try {
			getCamera().cancelAutoFocus();
		} catch (Exception e) {
		}
		try {
			getCamera().autoFocus(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeLight(boolean on) {
		Parameters p = getCamera().getParameters();
		if (on) {
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
		} else {
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
		}
		getCamera().setParameters(p);
	}

	public boolean isLightOn() {
		try {
			Parameters p = getCamera().getParameters();
			return (p.getFlashMode()
					.equalsIgnoreCase(Parameters.FLASH_MODE_TORCH));
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (autoFD >= 0) {
			if (handel == null) {
				handel = new Handler();
			}
			handel.postDelayed(this, autoFD);
		}
	}

	@Override
	public void setPreviewSize(int width, int height, int rotaion) {
		Camera.Size s;
		if (rotaion == 0 || rotaion == 180) {
			s = getBestPreviewSize(width, height);
		} else {
			s = getBestPreviewSize(height, width);
		}
		Camera.Parameters parameters = getCamera().getParameters();
		parameters.setPreviewSize(s.width, s.height);
		getCamera().setParameters(parameters);
	}

	public void setPreviewSize(int width, int height) {
		setPreviewSize(width, height, 0);
	}

	@Override
	public Camera.Size getBestPreviewSize(int width, int height) {
		Camera.Size result = null;
		Camera.Parameters p = getCamera().getParameters();
		for (Camera.Size size : p.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea > resultArea) {
						result = size;
					}
				}
			}
		}
		return result;
	}

	@Override
	public void autofocus() {
		autoFD = -1;
		try {
			getCamera().cancelAutoFocus();
		} catch (Exception e) {
		}
		try {
			getCamera().autoFocus(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		getCamera().autoFocus(this);
	}

}
