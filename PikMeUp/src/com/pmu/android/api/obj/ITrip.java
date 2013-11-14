package com.pmu.android.api.obj;

import java.util.Date;
import java.util.HashMap;

import android.graphics.Bitmap;

import com.pmu.android.api.IAction;

public interface ITrip {
	String getData();

	void setData(String newData);

	Date getTime();

	void setTime(Date d);

	boolean allowAdd(ITrip i, int time);

	boolean allowAdd(Date d, int time);

	void setQuanity(int i);

	int getQuanity();

	HashMap<String, Object> getExtras();

	Bitmap getImage();

	void addOnChangeCallback(ITripCallback itc);

	boolean removeOnChangeCallback(ITripCallback itc);

	IAction getAction();

	void setAction(IAction newAction);

	long getScanId();

	void setScanId(long scanId);
}
