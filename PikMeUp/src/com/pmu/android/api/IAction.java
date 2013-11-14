package com.pmu.android.api;

public interface IAction {

	String getTarget();

	String getName();

	void performAction();

	void addCallback(IActionCallback iac);

	void removeCallback(IActionCallback iac);

	void kill();

}
