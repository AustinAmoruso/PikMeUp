package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.impl.OfferResponseAction;

public class AcceptDialog extends DialogFragment {
	private Trip trip;

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

		return new AlertDialog.Builder(getActivity())
				.setTitle("Accept Offer")
				.setPositiveButton("Confirm Completion",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferResponseAction ora = new OfferResponseAction(
										getActivity(), trip, "accept");
								ora.performAction();
								dismiss();
							}
						})
				.setNegativeButton("Cancel Trip",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferResponseAction ora = new OfferResponseAction(
										getActivity(), trip, "pass");
								ora.performAction();
								dismiss();
							}
						}).setView(input).setMessage("Make this trip for")
				.create();
	}
}
