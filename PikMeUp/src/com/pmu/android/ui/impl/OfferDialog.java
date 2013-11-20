package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.transport.impl.OfferAction;

public class OfferDialog extends DialogFragment {

	private Request request;
	private Request match;

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setMatch(Request match) {
		this.match = match;
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
								OfferAction of = new OfferAction(getActivity(),
										request, input.getText().toString(),
										match);
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
