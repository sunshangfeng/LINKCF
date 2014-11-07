package com.ssf.linkcf;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.ssf.linkcf.fragment.IndexFragment;
import com.ssf.linkcf.fragment.LINKAddressFragment;
import com.ssf.linkcf.fragment.LINKBalanceFragment;
import com.ssf.linkcf.fragment.LINKCollectionFragment;
import com.ssf.linkcf.fragment.LINKOraderFragment;
import com.ssf.linkcf.fragment.LINKSettingFragment;
import com.ssf.linkcf.fragment.NavigationDrawerFragment;
import com.ssf.linkcf.helper.FragmentItemHelper;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	private FragmentItemHelper mFragmentHepler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragmentHepler = new FragmentItemHelper(this, getFragmentManager(),
				R.id.container);
		
		mFragmentHepler.addItem("index", IndexFragment.class, null);
		mFragmentHepler.addItem("orader", LINKOraderFragment.class, null);
		mFragmentHepler.addItem("balance", LINKBalanceFragment.class, null);
		mFragmentHepler.addItem("address", LINKAddressFragment.class, null);
		mFragmentHepler.addItem("collection", LINKCollectionFragment.class, null);
		mFragmentHepler.addItem("setting", LINKSettingFragment.class, null);
		
		setContentView(R.layout.activity_main);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		switch (position) {
		case NavigationDrawerFragment.DRAWER_IMAGE:
//			mFragmentHepler.setItemChanged(0);
			
			/**
			 * 为个人中心预留
			 */
			
			break;
		default:
			mFragmentHepler.setItemChanged(position);
			if (position == 0) {
				mTitle = getResources().getString(R.string.app_name);
			}else {
				mTitle =mNavigationDrawerFragment.getAdapterData()
						.get(position).get("title").toString();
			}
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean isDrawerOpen() {
		return mNavigationDrawerFragment.isDrawerOpen();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
}
