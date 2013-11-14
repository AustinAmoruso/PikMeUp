package com.pmu.android.api.transport.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.pmu.android.api.obj.impl.Location;
import com.pmu.android.api.transport.ITransportResponse;

public class PlaceDetailCommand extends JSONCommand {

	private Location location;
	private String referenceID;

	PlaceDetailCommand(String newReferenceID, Location newLocation)
			throws UnsupportedEncodingException {
		super(
				"https://maps.googleapis.com/maps/api/place/details/json?sensor=false&key="
						+ TransportContants.API_KEY + "&reference="
						+ URLEncoder.encode(newReferenceID, "utf8"));
		referenceID = newReferenceID;
		location = newLocation;
	}

	@Override
	public ITransportResponse Execute() {
		try {
			ITransportResponse itr = super.Execute();
			JSONObject json = (JSONObject) itr.getResponse();
			JSONObject loc = json.getJSONObject("result")
					.getJSONObject("address_components")
					.getJSONObject("geometry").getJSONObject("location");
			location.setLatitude(loc.getString("lat"));
			location.setLongitude(loc.getString("lng"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PlaceDetailResponse(location, referenceID);
	}

}
