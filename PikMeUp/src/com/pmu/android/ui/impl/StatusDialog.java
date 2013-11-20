package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.impl.TripStatusAction;

public class StatusDialog extends DialogFragment {

	private Trip trip;

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

		return new AlertDialog.Builder(getActivity())
				.setTitle("Complete Trip")
				.setPositiveButton("Confirm Completion",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								TripStatusAction sta = new TripStatusAction(
										getActivity(),
										"{\"complete\":{\"amount\":\""
												+ input.getText().toString()
												+ "\"}}", trip);
								sta.performAction();
								dismiss();
							}
						})
				.setNegativeButton("Cancel Trip",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								TripStatusAction sta = new TripStatusAction(
										getActivity(), "Cancelled", trip);
								sta.performAction();
								dismiss();
							}
						}).setView(input).setMessage("Final cost in Dollars")
				.create();
	}
}
