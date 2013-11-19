package com.pmu.android.ui.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmu.android.R;
import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.Trip;

public class FeedUI implements IObjectCallback, OnClickListener {

	public static final String MATCH = "Match";
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
		v.setOnClickListener(this);
		iv = (ImageView) v.findViewById(R.id.ivIcon);
		tvDate = (TextView) v.findViewById(R.id.txtDate);
		tvStart = (TextView) v.findViewById(R.id.txtStart);
		tvEnd = (TextView) v.findViewById(R.id.txtEnd);
		refresh();
	}

	private void image() {
		if (ifo instanceof Trip) {
			if (ifo.getType().equalsIgnoreCase(IFeedObject.DRIVE)) {
				iv.setImageResource(R.drawable.help);
			} else if (ifo.getType().equalsIgnoreCase(IFeedObject.RIDE)) {
				iv.setImageResource(R.drawable.account);
			}
		} else if (ifo instanceof Request) {
			if (ifo.getType().equalsIgnoreCase(IFeedObject.DRIVE)) {
				iv.setImageResource(R.drawable.drive);
			} else if (ifo.getType().equalsIgnoreCase(IFeedObject.RIDE)) {
				iv.setImageResource(R.drawable.ride);
			}
		}

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

	@Override
	public void onClick(View v) {
		if (ApiFactory.getObjectFactory(context).getRequests().getSelected() == null) {
			ApiFactory.getObjectFactory(context).getRequests().setSelected(ifo);
		} else {
			ApiFactory.getObjectFactory(context).getRequests()
					.update(MATCH, ifo);
		}
	}

}
