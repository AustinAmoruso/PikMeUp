package com.pmu.android.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Util {

	public static byte[] combine(byte[] a, byte[] b) {
		byte[] result = new byte[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(.\\d+)?");
	}

	public static String date(int month, int year) {
		return month + "/" + year;
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.trim().equalsIgnoreCase("")) {
			return true;
		} else if (str.equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isUrl(String data) {
		data = data.trim();
		return (hasNoSpaces(data) && data
				.matches("^(https?://)?([da-z.-]+).([a-z.]{2,6})([/w .-]*)*/?$"));
	}

	public static boolean hasNoSpaces(String data) {
		return (!data.contains(" "));
	}

	public static String makeUrl(String data) {
		return String.format("http://%S", data);
	}

	public static boolean isContact(String data) {
		String d = data.toLowerCase();
		return (d.contains("begin:vcard") || (d.contains("mecard")));
	}

	public static boolean isPhone(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isEmail(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isEvent(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isWiFi(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isLocation(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isSMS(String data) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean versionCheck(String versionCurrent, String versionMin) {
		ArrayList<Integer> vcl = getVersionNumbers(versionCurrent);
		ArrayList<Integer> vml = getVersionNumbers(versionMin);
		for(int i = 0; i< vml.size(); i++){
			if(vcl.get(i) > vml.get(i)){
				return true;
			}
		}
		return(vcl.size()>=vml.size());
	}
	
	private static ArrayList<Integer> getVersionNumbers(String version){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(String s : version.split(".")){
			result.add(Integer.valueOf(s));
		}
		return result;
	}
}
