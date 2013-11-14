package com.pmu.android.api;

import android.content.Context;

public class ApiFactory {

	private static AdapterFactory adapterFactory;
	private static StorageFactory storageFactory;
	private static TransportFactory transportFactory;
	private static ObjectFactory objectFactory;

	public static AdapterFactory getAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new AdapterFactory();
		}
		return adapterFactory;
	}

	public static StorageFactory getStrorageFactory(Context c) {
		if (storageFactory == null) {
			storageFactory = new StorageFactory(c);
		}
		return storageFactory;
	}

	public static TransportFactory getTransportFactory() {
		if (transportFactory == null) {
			transportFactory = new TransportFactory();
		}
		return transportFactory;
	}

	public static ObjectFactory getObjectFactory(Context c) {
		if (objectFactory == null) {
			objectFactory = new ObjectFactory(c);
		}
		return objectFactory;
	}

	public static void resetFactory() {
		adapterFactory = null;
		storageFactory = null;
		transportFactory = null;
		objectFactory = null;
	}
}
