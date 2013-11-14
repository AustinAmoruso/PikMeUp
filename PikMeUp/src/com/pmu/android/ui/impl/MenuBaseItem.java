package com.pmu.android.ui.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmu.android.R;
import com.pmu.android.ui.IMenuUI;

public abstract class MenuBaseItem implements IMenuUI {

	private String name;
	private int image;
	private Context context;

	public MenuBaseItem(String newName, int newImage) {
		name = newName;
		image = newImage;
	}

	@Override
	public View getView(Context newContext) {
		context = newContext;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View result = null;
		result = inflater.inflate(R.layout.menuitem, null, false);
		ImageView img = (ImageView) result.findViewById(R.id.imgicon);
		img.setImageResource(image);
		TextView txt = (TextView) result.findViewById(R.id.txtlabel);
		txt.setText(name);
		return result;
	}

	@Override
	public abstract Object getContents();

	public Context getContext() {
		return context;
	}

}
