package com.pmu.android.ui.impl;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pmu.android.ui.IMenuUI;

public class MenuArrayAdapter extends ArrayAdapter<IMenuUI> {

	public MenuArrayAdapter(Context context, int textViewResourceId,
			ArrayList<IMenuUI> objects) {
		super(context, textViewResourceId, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return getItem(position).getView(getContext());
	}

}