package com.pmu.android.api.transport.impl;

public class TransportContants {

	// Production

	public static final String BASEURL = "pikmeup-developer-edition.na15.force.com";
	public static final String PROTOCAL = "https://";
	public static final String DEST = "/services/Soap/class/";
	public static final String NAME = "PikMeUpSoapService";
	public static final String NAMESPACE = "http://soap.sforce.com/schemas/class/PikMeUpSoapService";
	// Test
	public static final String DEMO_BASEURL = "place4expenses.full.cs9.force.com";
	public static final String DEMO_PROTOCAL = "http://";
	public static final String DEMO_DEST = "/a/services/Soap/class/";
	public static final String DEMO_NAME = "SnapBiService";
	public static final String DEMO_NAMESPACE = "http://soap.sforce.com/schemas/class/SnapBiService";

	public static final String FULLURL = PROTOCAL + BASEURL + DEST + NAME;
	public static final String DEMO_FULLURL = DEMO_PROTOCAL + DEMO_BASEURL
			+ DEMO_DEST + DEMO_NAME;

	public static final String BUCKET = "pikmeup";
	public static final String USERNAME = "pikmeup";
	public static final String ACCESS_KEY_ID = "AKIAI5UTO622NXHIRLAA";
	public static final String SECERT_ACCESS_KEY = "UEr7NFvgWIgzZ6xlF/kB0bJdQp20ezstUMkG5qFI";
	public static final String GCM_KEY = "159398720323";

	public static final String API_KEY = "AIzaSyCGEJusiPuVGcw-Tybqij32oJDUCJ-2J2Q";

}