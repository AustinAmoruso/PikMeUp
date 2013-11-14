package com.pmu.android.api.obj.impl;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pmu.android.api.obj.IAsncObject;
import com.pmu.android.api.obj.IObjectCallback;

public class BaseAsyncObject implements IAsncObject, IObjectCallback {

	private CopyOnWriteArrayList<IObjectCallback> callbacks;
	private AtomicBoolean needsPush;

	public BaseAsyncObject() {
		callbacks = new CopyOnWriteArrayList<IObjectCallback>();
		needsPush = new AtomicBoolean();
	}

	public synchronized boolean push() {
		boolean result = needsPush.get();
		pushed();
		return result;
	}

	private synchronized void pushed() {
		needsPush.set(false);
	}

	public synchronized void resetPush() {
		needsPush.set(true);
	}

	@Override
	public synchronized void addOnActionCallback(IObjectCallback icb) {
		callbacks.add(icb);
	}

	@Override
	public synchronized boolean removeOnActionCallBack(IObjectCallback icb) {
		boolean result = false;
		result = callbacks.remove(icb);
		return result;
	}

	public synchronized void onUpdate(String action, Object value) {
		resetPush();
		for (IObjectCallback icc : callbacks) {
			try {
				icc.onAction(action, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onAction(String action, Object value) {
		onUpdate(action, value);
	}

}
