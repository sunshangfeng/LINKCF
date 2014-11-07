package com.ssf.linkcf.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ssf.linkcf.R;
import com.ssf.linkcf.view.ActionBarDrawerToggle;
import com.ssf.linkcf.view.CircularImageView;
import com.ssf.linkcf.view.DrawerArrowDrawable;

public class NavigationDrawerFragment extends Fragment {

	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
	
	public static final int DRAWER_IMAGE = -1;
	
	private NavigationDrawerCallbacks mCallbacks;

	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerArrowDrawable drawerArrow;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	

	private List<Map<String, Object>> setAdapterData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("icon",R.drawable.ic_launcher);
		map.put("title", getResources().getString(R.string.app_name));
		list.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("icon",R.drawable.ic_launcher);
		map1.put("title", getResources().getString(R.string.title_order));
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("icon",R.drawable.ic_launcher);
		map2.put("title", getResources().getString(R.string.title_balance));
		list.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("icon",R.drawable.ic_launcher);
		map3.put("title", getResources().getString(R.string.title_address));
		list.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("icon",R.drawable.ic_launcher);
		map4.put("title", getResources().getString(R.string.title_collection));
		list.add(map4);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("icon",R.drawable.ic_launcher);
		map5.put("title", getResources().getString(R.string.title_setting));
		list.add(map5);
		
		return list;
	}
	
	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}
		setAdapterData();
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (View) inflater.inflate(
				R.layout.fragment_navigation_drawer, container, false);
		
		CircularImageView mImageView =  (CircularImageView) view.findViewById(R.id.fragment_navigation_image);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectItem(DRAWER_IMAGE);
			}
		});
		
		mDrawerListView = (ListView) view.findViewById(R.id.fragment_navigation_list);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});

		mDrawerListView.setAdapter(new SimpleAdapter(getActivity(), list,
				R.layout.fragment_navigation_list_item, new String[] {"icon","title"},
				new int[] {R.id.imageView1,R.id.text1}));
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return view;
	}
	
	public List<Map<String, Object>> getAdapterData() {
		return list;
	}
	
	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		drawerArrow = new DrawerArrowDrawable(getActivity()) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
		mDrawerLayout,
		drawerArrow,
		R.string.navigation_drawer_open, 
		R.string.navigation_drawer_close 
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}

				getActivity().invalidateOptionsMenu(); 
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.apply();
				}

				getActivity().invalidateOptionsMenu(); 
			}
		};

		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (mDrawerLayout != null && isDrawerOpen()) {
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		if (mDrawerLayout != null && !isDrawerOpen()) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		return super.onOptionsItemSelected(item);
	}

	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return getActivity().getActionBar();
	}

	public static interface NavigationDrawerCallbacks {
		void onNavigationDrawerItemSelected(int position);
	}
}
