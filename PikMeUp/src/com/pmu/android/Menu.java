package com.pmu.android;

import java.util.ArrayList;

import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.pmu.android.ui.IMenuUI;
import com.pmu.android.ui.impl.MenuAccountUI;
import com.pmu.android.ui.impl.MenuArrayAdapter;
import com.pmu.android.ui.impl.MenuDrivesUI;
import com.pmu.android.ui.impl.MenuHelpUI;
import com.pmu.android.ui.impl.MenuRidesUI;

public class Menu extends ListFragment {

	private MenuArrayAdapter adapter;
	private ArrayList<IMenuUI> items;

	public void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		items = new ArrayList<IMenuUI>();
		items.add(new MenuDrivesUI());
		items.add(new MenuRidesUI());
		items.add(new MenuAccountUI());
		items.add(new MenuHelpUI());
		adapter = new MenuArrayAdapter(getActivity(), 0, items);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		try {
			Drawer s = (Drawer) getActivity();
			s.closeMenu();
			Object o = adapter.getItem(position).getContents();
			s.SwapFragmentByClass((String) o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
