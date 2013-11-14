package com.pmu.android.api;

import java.util.ArrayList;

public abstract class BaseAction implements IAction {

	private ArrayList<IActionCallback> callbacks;
	private String target;

	public BaseAction(String newTarget) {
		target = newTarget;
		callbacks = new ArrayList<IActionCallback>();
	}

	public BaseAction() {
		callbacks = new ArrayList<IActionCallback>();
	}

	@Override
	public void addCallback(IActionCallback iac) {
		callbacks.add(iac);
	}

	@Override
	public void removeCallback(IActionCallback iac) {
		callbacks.remove(iac);
	}

	public void Notify(Object obj) {
		for (IActionCallback icb : callbacks) {
			try {
				icb.onComplete(obj);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public final ArrayList<IActionCallback> getCallbacks() {
		return callbacks;
	}

	@Override
	public String getTarget() {
		return target;
	}

}
