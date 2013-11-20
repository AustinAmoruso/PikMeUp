package com.pmu.android.ui.impl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pmu.android.R;
import com.pmu.android.api.ApiFactory;
import com.pmu.android.api.obj.IFeedObject;
import com.pmu.android.api.obj.IObjectCallback;
import com.pmu.android.api.obj.impl.Request;
import com.pmu.android.api.obj.impl.Trip;

public class FeedUI implements IObjectCallback, OnClickListener,
		OnLongClickListener {

	public static final String MATCH = "Match";
	IFeedObject ifo;
	View v;
	Context context;
	ImageView iv;
	TextView tvDate;
	TextView tvStart;
	TextView tvEnd;
	boolean clickable;

	public FeedUI(IFeedObject newIFO, Context newContext) {
		ifo = newIFO;
		context = newContext;
		initView();
		ifo.addOnActionCallback(this);
		clickable = true;
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.request_item, null, false);
		v.setOnClickListener(this);
		v.setOnLongClickListener(this);
		iv = (ImageView) v.findViewById(R.id.ivIcon);
		tvDate = (TextView) v.findViewById(R.id.txtDate);
		tvStart = (TextView) v.findViewById(R.id.txtStart);
		tvEnd = (TextView) v.findViewById(R.id.txtEnd);
		refresh();
	}

	private void image() {
		if (ifo instanceof Trip) {
			if (ifo.getType().equalsIgnoreCase(Trip.DRIVE)) {
				iv.setImageResource(R.drawable.help);
			} else if (ifo.getType().equalsIgnoreCase(Trip.RIDE)) {
				iv.setImageResource(R.drawable.account);
			}
		} else if (ifo instanceof Request) {
			if (ifo.getType().equalsIgnoreCase(Request.DRIVE)) {
				iv.setImageResource(R.drawable.drive);
			} else if (ifo.getType().equalsIgnoreCase(Request.RIDE)) {
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
		if (clickable) {
			if (ApiFactory.getObjectFactory(context).getRequests()
					.getSelected() == null) {
				ApiFactory.getObjectFactory(context).getRequests()
						.setSelected(ifo);
			} else {
				ApiFactory.getObjectFactory(context).getRequests()
						.update(MATCH, ifo);
			}
		} else {
			// Toast.makeText(context, "boch", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (clickable) {
			if (ifo instanceof Request) {
				if (ApiFactory.getObjectFactory(context).getRequests()
						.getSelected() != null) {
					if (ifo.getType().equalsIgnoreCase(Request.RIDE)) {
						// set as match
						MatchDialog md = new MatchDialog();
						md.setRequest((Request) ApiFactory
								.getObjectFactory(context).getRequests()
								.getSelected());
						md.setMatch((Request) ifo);
						Activity a = (Activity) context;
						md.show(a.getFragmentManager(), "MatchDialog");
					} else if (ifo.getType().equalsIgnoreCase(Request.DRIVE)) {
						// make offer
						OfferDialog of = new OfferDialog();
						of.setRequest((Request) ApiFactory
								.getObjectFactory(context).getRequests()
								.getSelected());
						of.setMatch((Request) ifo);
						Activity a = (Activity) context;
						of.show(a.getFragmentManager(), "OfferDialog");
					}
				} else {

				}
			} else if (ifo instanceof Trip) {
				Trip t = (Trip) ifo;
				if (t.getStatus().equalsIgnoreCase("Match")
						&& t.getType().equalsIgnoreCase(Request.RIDE)) {
					// make offer
					OfferMDialog of = new OfferMDialog();
					of.setTrip(t);
					Activity a = (Activity) context;
					of.show(a.getFragmentManager(), "OfferMDialog");
				} else if (t.getStatus().equalsIgnoreCase("Offer")
						&& t.getType().equalsIgnoreCase(Request.DRIVE)) {
					// Accept or Pass
					Toast.makeText(context, "boch", Toast.LENGTH_LONG).show();
				} else if (t.getStatus().equalsIgnoreCase("Offer")
						&& t.getType().equalsIgnoreCase(Request.RIDE)) {
					// Cancle
					CancelDialog cd = new CancelDialog();
					cd.setTrip(t);
					Activity a = (Activity) context;
					cd.show(a.getFragmentManager(), "CancelDialog");
				} else if (t.getStatus().equalsIgnoreCase("Started")
						&& t.getType().equalsIgnoreCase(Request.DRIVE)) {
					// completed
					StatusDialog sd = new StatusDialog();
					sd.setTrip(t);
					Activity a = (Activity) context;
					sd.show(a.getFragmentManager(), "StatusDialog");
				} else if (t.getStatus().equalsIgnoreCase("Accepted")
						&& t.getType().equalsIgnoreCase(Request.DRIVE)) {
					// Start or cancel
					StartDialog sd = new StartDialog();
					sd.setTrip(t);
					Activity a = (Activity) context;
					sd.show(a.getFragmentManager(), "StartDialog");
				} else if (t.getStatus().equalsIgnoreCase("Accepted")
						&& t.getType().equalsIgnoreCase(Request.RIDE)) {
					// cancel
					CancelDialog cd = new CancelDialog();
					cd.setTrip(t);
					Activity a = (Activity) context;
					cd.show(a.getFragmentManager(), "CancelDialog");
				}
			}
		} else {
			// Toast.makeText(context, "boch", Toast.LENGTH_LONG).show();
		}
		return true;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
}
