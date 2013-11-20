package com.pmu.android.api.obj;

import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.obj.impl.Time;

public interface IFeedObject extends IAsncObject {



	public Location getStart();

	public void setStart(Location start);

	public Location getEnd();

	public void setEnd(Location end);

	public String getFlex();

	public void setFlex(String flex);

	public String getType();

	public void setType(String type);

	public Time getTime();

	public void setTime(Time time);

	public String getID();

	public void setID(String trip_ID);

}
