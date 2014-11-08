package com.ssf.linkcf.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ssf.linkcf.MainActivity;
import com.ssf.linkcf.R;
import com.ssf.linkcf.bean.ForeignerBean;
import com.ssf.linkcf.helper.NetWorkPostHelper;
import com.ssf.linkcf.helper.NetWorkPostHelper.OnNetWorkListener;

public class IndexFragment extends ProgressListFragment implements
		OnNetWorkListener {

	private ListView mListView;

	private ImageView image;

	private NetWorkPostHelper mNetWork;

	private MyAdapter listAdapter;

	private DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(100))
		.build();

		setHasOptionsMenu(true);

		mNetWork = new NetWorkPostHelper(getActivity());

		mNetWork.setOnNetWorkListener(this);

		listAdapter = new MyAdapter();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (listAdapter.getCount() == 0) {
			obtainData();
		}

	}

	private void obtainData() {
		setListShown(false);
		Map<String, Object> map = new HashMap<>();
		map.put("Method", "foreigner.getforeignerlist");
		map.put("Lng", "116.403875");
		map.put("Lat", "39.915168");
		map.put("Page", 0);
		map.put("PageNum", 10);
		mNetWork.startNetWork(map);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mListView = getListView();
		image = new ImageView(getActivity());
		image.setImageResource(R.drawable.img_header);
		image.setScaleType(ScaleType.FIT_XY);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 400);
		image.setLayoutParams(params);
		mListView.addHeaderView(image);
		setListAdapter(listAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_sort) {
			VolleyLog.d("%s", "点击");
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

	@Override
	public void onResponse(Object arg0) {
		List<ForeignerBean> list = new ArrayList<ForeignerBean>();
		list = new Gson().fromJson(arg0.toString(),
				new TypeToken<List<ForeignerBean>>() {
				}.getType());
		listAdapter.setData(list);
		setListShown(true);
	}

	@Override
	public void onErrorResponse(String arg0) {
		if (!arg0.isEmpty()) {
			setListShown(true);
			setEmptyText(arg0);
		}
	}

	public class MyAdapter extends BaseAdapter {

		private List<ForeignerBean> mlist = new ArrayList<ForeignerBean>();
		
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		@Override
		public int getCount() {
			return mlist.size();
		}

		public void setData(List<ForeignerBean> list) {
			if (!list.isEmpty()) {
				mlist.addAll(list);
				notifyDataSetChanged();
			}
		}

		public ForeignerBean getItemData(int p) {
			return mlist.get(p);
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.fragment_index_list_item, null, false);
			}
			ImageView image = (ImageView) convertView
					.findViewById(R.id.index_item_title_image);
			TextView title = (TextView) convertView
					.findViewById(R.id.index_item_title_text);
			TextView content = (TextView) convertView
					.findViewById(R.id.index_item_content_text);
			ForeignerBean foreignerBean = getItemData(position);
			StringBuffer str = new StringBuffer();
			str.append("价格:"+foreignerBean.getUnitPrice()+"/RMB每小时");
			str.append("\n状态:"+foreignerBean.getFreeTime()+"小时可预约");
			str.append("\n经验:"+foreignerBean.getClassCount()+"课时");
			title.setText(foreignerBean.getNickName());
			content.setText(str);
			ImageLoader.getInstance().displayImage(foreignerBean.getPortrait(), image, options, animateFirstListener);
			
			return convertView;
		}

	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
