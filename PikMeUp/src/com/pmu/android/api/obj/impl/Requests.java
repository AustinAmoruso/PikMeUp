package com.pmu.android.api.obj.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import com.pmu.android.api.obj.IFeedObject;

public class Requests extends BaseAsyncObject {

	public static final String SELECTED = "Selected";
	public static final String REMOVE = "remove";
	public static final String ADD = "add";
	public static final String CLEAR = "clear";
	private CopyOnWriteArrayList<IFeedObject> contents;
	private IFeedObject selected;

	public Requests() {
		super();
		contents = new CopyOnWriteArrayList<IFeedObject>();
		selected = null;
	}

	public synchronized void Clear() {
		contents.clear();
		selected = null;
		onUpdate(CLEAR, null);
	}

	public synchronized boolean add(IFeedObject i) {
		boolean result = contents.add(i);
		i.addOnActionCallback(this);
		onUpdate(ADD, i);
		return result;
	}

	public synchronized final CopyOnWriteArrayList<IFeedObject> getIFeedObjects() {
		return contents;
	}

	public synchronized boolean removeItem(IFeedObject i) {
		boolean result = false;
		result = contents.remove(i);
		i.removeOnActionCallBack(this);
		onUpdate(REMOVE, i);
		return result;
	}

	public synchronized void setSelected(IFeedObject i) {
		selected = i;
		onUpdate(SELECTED, i);
	}

	public synchronized IFeedObject getSelected() {
		return selected;
	}

	public synchronized void update(String name, Object val) {
		onUpdate(name, val);
	}
}
