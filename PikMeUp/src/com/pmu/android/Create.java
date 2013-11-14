package com.pmu.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pmu.android.api.IAction;
import com.pmu.android.api.IActionCallback;
import com.pmu.android.api.transport.impl.CreateUserAction;

public class Create extends Fragment implements OnClickListener,
		IActionCallback {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.create, container, false);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		initButton();
	}

	private void initButton() {
		Button btnRegister = (Button) getView().findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String username = ((EditText) getView().findViewById(R.id.edtUsername))
				.getText().toString();
		String password = ((EditText) getView().findViewById(R.id.edtPassword))
				.getText().toString();
		IAction a = new CreateUserAction(username, password, getActivity());
		a.addCallback(this);
		a.performAction();
	}

	@Override
	public void onComplete(Object result) {
		if (result instanceof String) {
			String val = (String) result;
			if (val.equalsIgnoreCase(CreateUserAction.CREATE_USER_ACTION_SUCCESS)) {
				Drawer d = (Drawer) getActivity();
				d.SwapFragmentByClass(Map.class.getName());
			} else if (val
					.equalsIgnoreCase(CreateUserAction.CREATE_USER_ACTION_FAILURE)) {

			}
		}
	}

}
