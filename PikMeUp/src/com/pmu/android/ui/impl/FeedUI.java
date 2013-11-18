package com.pmu.android.ui.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmu.android.R;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.IObjectCallback;

public class FeedUI implements IObjectCallback {

	IFeedObject ifo;
	View v;
	Context context;
	ImageView iv;
	TextView tvDate;
	TextView tvStart;
	TextView tvEnd;

	public FeedUI(IFeedObject newIFO, Context newContext) {
		ifo = newIFO;
		context = newContext;
		initView();
		ifo.addOnActionCallback(this);
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.request_item, null, false);
		iv = (ImageView) v.findViewById(R.id.ivIcon);
		tvDate = (TextView) v.findViewById(R.id.txtDate);
		tvStart = (TextView) v.findViewById(R.id.txtStart);
		tvEnd = (TextView) v.findViewById(R.id.txtEnd);
		refresh();
	}

	private void image() {
		// iv.setImageBitmap(bm)
	}

	private void date() {
		tvDate.setText(ifo.getTime().toString());
	}

	private void start() {
		tvStart.setText(ifo.getStart().getAlias());
	}

	private void end() {
		tvEnd.setText(ifo.getEnd().getAlias());
	}

	private void refresh() {
		image();
		date();
		start();
		end();
	}

	public View getView() {
		return v;
	}

	@Override
	public void onAction(String action, Object value) {
		refresh();
	}

}
