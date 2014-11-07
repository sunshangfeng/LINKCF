package com.ssf.linkcf.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

import com.android.volley.VolleyLog;
import com.ssf.linkcf.MainActivity;
import com.ssf.linkcf.R;

public class IndexFragment extends Fragment {

	String[] dummys = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18" };
	private ListView parallax;
	private ImageView image;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		parallax = (ListView) view.findViewById(R.id.fragment_index_listView);

		parallax.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, dummys));

		image = new ImageView(getActivity());
		image.setImageResource(R.drawable.img_header);
		image.setScaleType(ScaleType.FIT_XY);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
		image.setLayoutParams(params);
		parallax.addHeaderView(image);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_sort) {
			VolleyLog.d("%s", "µã»÷");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MainActivity activity = (MainActivity) getActivity();
		if (!activity.isDrawerOpen()) {
			activity.restoreActionBar();
			inflater.inflate(R.menu.main, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		
	}
}
