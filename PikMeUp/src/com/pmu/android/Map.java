package com.pmu.android;

import java.io.File;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.google.android.gms.maps.MapView;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;
import com.pmu.android.api.transport.impl.AsyncTransportCalls;
import com.pmu.android.api.transport.impl.TransportContants;
import com.pmu.android.util.SoapParser;

public class Map extends Fragment implements ITransportCallBack {

	private static final int CODE = 110101;
	MapView m;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.map, container, false);
		m = (MapView) rootView.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		m.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		m.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		m.onLowMemory();
	}

	private void registerGCM() {
		AsyncTransportCalls.registerGCM(this);
	}

	public void pickImage(View View) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
			Uri selectedImageUri = data.getData();
			String path = getPath(selectedImageUri);
			File f = new File(path);
			AsyncTransportCalls.uploadFile(f, "thisisatest", this);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase("thisisatest")) {
			try {
				UploadResult ur = (UploadResult) response.getResponse();
				Log.e("Bucket:", ur.getBucketName());
				Log.e("Tag:", ur.getETag());
				Log.e("Key:", ur.getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (response.getCaller().equalsIgnoreCase(
				TransportContants.GCM_KEY)) {
			String ri = (String) response.getResponse();
			ri.toString();
		} else if (response.getCaller().equalsIgnoreCase(
				AsyncTransportCalls.CREATE_USER)) {
			SoapObject so = (SoapObject) response.getResponse();
			String json = SoapParser.getContent(so);
			json.toCharArray();
		}
	}
}