package com.pmu.android.api.storage;

public interface IStorage {

	public Object getValue(String key);

	public void setValue(String key, Object value);
}
