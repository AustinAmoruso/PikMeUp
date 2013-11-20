package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.impl.OfferMAction;

public class OfferMDialog extends DialogFragment {

	private Trip trip;

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

		return new AlertDialog.Builder(getActivity())
				.setTitle("Make Offer")
				.setPositiveButton("Bid",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferMAction of = new OfferMAction(
										getActivity(), input.getText()
												.toString(), trip);
								of.performAction();
								dismiss();
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
