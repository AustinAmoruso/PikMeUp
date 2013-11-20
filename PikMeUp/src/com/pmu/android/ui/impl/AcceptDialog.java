package com.pmu.android.ui.impl;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.obj.impl.Trip;
import com.pmu.android.api.transport.impl.DownloadFileAction;
import com.pmu.android.api.transport.impl.OfferResponseAction;

public class AcceptDialog extends DialogFragment implements IActionCallback {
	private Trip trip;
	private AcceptDialog ad;
	private Context c;

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ad = this;
		c = getActivity();
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
				.setTitle("Accept Offer")
				.setPositiveButton("Confirm Offer",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferResponseAction ora = new OfferResponseAction(
										getActivity(), trip, "accept");
								ora.performAction();
								dismiss();
							}
						})
				.setNegativeButton("Pass Offer",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								OfferResponseAction ora = new OfferResponseAction(
										c, trip, "pass");
								ora.performAction();
								dismiss();
							}
						});
		if (trip.getOffer() != null && trip.getDuration() != null) {
			String message = String.format("Amount:%s Time:%s",
					trip.getOffer(), trip.getDuration());
			b.setMessage(message);
		}
		if (trip.getFlit() != null) {
			b.setNeutralButton("Flit", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					// get flit then play
					String flitId = trip.getFlit();
					File mediaFile = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/flitDown.mp4");
					DownloadFileAction dfa = new DownloadFileAction(c, flitId,
							mediaFile);
					dfa.addCallback(ad);
					dfa.performAction();
				}
			});
		}

		return b.create();
	}

	@Override
	public void onComplete(Object result) {
		if (result instanceof File) {
			File file = (File) result;
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "video/*");
			Activity a = (Activity) c;
			a.startActivity(intent);
		}
	}
}
