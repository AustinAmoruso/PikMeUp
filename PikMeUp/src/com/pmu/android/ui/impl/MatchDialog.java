package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.transport.impl.OfferAction;

public class MatchDialog extends DialogFragment {

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

		return new AlertDialog.Builder(getActivity())
				.setTitle("Set as Match")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferAction of = new OfferAction(getActivity(),
										request, "none", match);
								of.performAction();
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
