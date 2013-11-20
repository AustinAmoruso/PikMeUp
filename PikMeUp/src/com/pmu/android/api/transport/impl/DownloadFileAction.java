package com.pmu.android.api.transport.impl;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;

import com.pmu.android.api.BaseAction;
import com.pmu.android.api.transport.ITransportCallBack;
import com.pmu.android.api.transport.ITransportResponse;

public class DownloadFileAction extends BaseAction implements
		ITransportCallBack {

	private String name;
	private File file;
	private ProgressDialog pd;
	private Context context;

	public DownloadFileAction(Context newContext, String newName, File newFile) {
		name = newName;
		file = newFile;
		context = newContext;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void performAction() {
		pd = ProgressDialog.show(context, "Retrieving Flit",
				"Please wait as the flit is being retrieved");
		AsyncTransportCalls.downloadFile(file, name, this);
	}

	@Override
	public void kill() {
	}

	@Override
	public void onCallback(ITransportResponse response) {
		if (response.getCaller().equalsIgnoreCase(name)) {
			pd.dismiss();
			Notify(file);
		}
	}

}
