package com.pmu.android.ui.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.transport.impl.TransportContants;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<Location> implements
		Filterable {

	private ArrayList<Location> resultList;
	private static final String LOG_TAG = "ExampleApp";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private String lat, lng;
	private String radius;

	private ArrayList<Location> autocomplete(String input) {
		ArrayList<Location> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE
					+ TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?sensor=false&key=" + TransportContants.API_KEY);
			// sb.append("&" + URLEncoder.encode("components=country:us",
			// "utf8"));

			sb.append("&location=" + URLEncoder.encode(lat, "utf8")
					+ URLEncoder.encode(",", "utf8")
					+ URLEncoder.encode(lng, "utf8"));
			sb.append("&radius=" + URLEncoder.encode(radius, "utf8"));
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));
			Log.e("**************", sb.toString());

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<Location>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				Location l = new Location(predsJsonArray.getJSONObject(i)
						.getString("description"));
				l.setRequestID(predsJsonArray.getJSONObject(i).getString(
						"reference"));
				resultList.add(l);
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;
	}

	public PlacesAutoCompleteAdapter(Context context, int textViewResourceId,
			String newLat, String newLng, String newRadius) {
		super(context, textViewResourceId);
		lat = newLat;
		lng = newLng;
		radius = newRadius;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public Location getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					resultList = autocomplete(constraint.toString());

					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}
