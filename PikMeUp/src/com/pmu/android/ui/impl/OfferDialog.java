package com.pmu.android.ui.impl;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.NumberPicker;

import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.transport.impl.OfferAction;

public class OfferDialog extends DialogFragment {

	public static final int ACTION_TAKE_VIDEO = 5;
	private Request request;
	private Request match;
	ProgressDialog pd;

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setMatch(Request match) {
		this.match = match;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final NumberPicker input = new NumberPicker(getActivity());
		input.setMaxValue(9001);
		input.setMinValue(0);
		input.setValue(5);

		return new AlertDialog.Builder(getActivity())
				.setTitle("Make Offer")
				.setPositiveButton("Bid",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferAction of = new OfferAction(getActivity(),
										request, String.valueOf(input
												.getValue()), match);
								of.performAction();
								dismiss();
							}
						})
				.setNeutralButton("Flit",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pd = ProgressDialog.show(getActivity(),
										"Initalizing Flit",
										"Please wait for the camera");
								final OfferAction of = new OfferAction(
										getActivity(), request, String
												.valueOf(input.getValue()),
										match);
								of.setActivity(getActivity());
								of.addCallback(new IActionCallback() {

									@Override
									public void onComplete(Object result) {
										pd.dismiss();
										File mediaFile = new File(Environment
												.getExternalStorageDirectory()
												.getAbsolutePath()
												+ "/flitUp.mp4");

										Intent intent = new Intent(
												MediaStore.ACTION_VIDEO_CAPTURE);
										Uri videoUri = Uri.fromFile(mediaFile);

										intent.putExtra(
												MediaStore.EXTRA_OUTPUT,
												videoUri);
										ApiFactory
												.getStrorageFactory(
														of.getActivity())
												.getPreferences()
												.setValue("request",
														request.getID());
										ApiFactory
												.getStrorageFactory(
														of.getActivity())
												.getPreferences()
												.setValue("match",
														match.getID());
										Activity a = of.getActivity();
										a.startActivityForResult(intent,
												ACTION_TAKE_VIDEO);
									}
								});
								of.performAction();

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dismiss();
							}
						}).setView(input).setMessage("Amount in Dollars")
				.create();
	}

}
