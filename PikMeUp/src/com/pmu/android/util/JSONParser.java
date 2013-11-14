package com.pmu.android.util;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

public class JSONParser {

	public static String getArray(String json) {
		return json.substring(json.indexOf('{') + 1, json.lastIndexOf('}') - 1);
	}

	public static String jsonObject(String container,
			HashMap<String, String> hmp) {
		String result = "";
		for (String k : hmp.keySet()) {
			result += nameValuePair(k, hmp.get(k)) + ",";
		}
		result = result.substring(0, result.length() - 1);
		result = wrapObject(container, result);
		return result;
	}

	public static String jsonKV(HashMap<String, String> hmp) {
		String result = "";
		for (String k : hmp.keySet()) {
			result += nameValuePair(k, hmp.get(k)) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return String.format("{%s}", result);
	}

	public static String wrapObject(String container, String value) {
		return String.format("{\"%s\":{%s}}", container, value);
	}

	public static String nameValuePair(String name, String value) {
		return String.format("\"%s\":\"%s\"", name, value);
	}

	public static JSONObject getObject(String name,
			HashMap<String, String> values) {
		JSONObject result = null;
		try {
			JSONObject jo = new JSONObject();
			for (String k : values.keySet()) {
				String temp = JSONObject.quote(values.get(k));
				temp = temp.substring(1, temp.length() - 1);
				jo.put(k, temp);
			}
			result = new JSONObject();
			result.put(name, jo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject getValues(HashMap<String, String> values) {
		JSONObject result = null;
		try {
			result = new JSONObject();
			for (String k : values.keySet()) {
				String temp = JSONObject.quote(values.get(k));
				temp = temp.substring(1, temp.length() - 1);
				result.put(k, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject mergeObjects(JSONObject a, JSONObject b) {
		JSONObject merged = new JSONObject();
		try {
			JSONObject[] objs = new JSONObject[] { a, b };
			for (JSONObject obj : objs) {
				Iterator it = obj.keys();
				while (it.hasNext()) {
					String key = (String) it.next();
					merged.put(key, obj.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merged;
	}
}