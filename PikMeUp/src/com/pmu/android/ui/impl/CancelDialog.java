package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.impl.TripStatusAction;

public class CancelDialog extends DialogFragment {
	private Trip trip;

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setTitle("Cancel Trip")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								TripStatusAction sta = new TripStatusAction(
										getActivity(), "Cancelled", trip);
								sta.performAction();
								dismiss();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						dismiss();
					}
				}).create();
	}
}
