package com.pmu.android.ui.impl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.NumberPicker;

import com.pmu.android.R;

public class FlexPickerFragment extends DialogFragment implements
		OnClickListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO: Make this more flexible
		NumberPicker np = new NumberPicker(getActivity());
		np.setMaxValue(1440);
		np.setMinValue(0);
		np.setValue(5);

		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.flex_picker_title).setView(np)
				.setPositiveButton(android.R.string.ok, this).create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
