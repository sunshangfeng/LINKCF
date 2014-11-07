package com.ssf.linkcf.helper;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

public class FragmentItemHelper {

	private Context mContext;
	private FragmentManager mManager;
	private int mContainerId;

	private FragmentInfo mLastFragment;

	private String mCurrentTabTag;

	private final ArrayList<FragmentInfo> mFragments = new ArrayList<FragmentInfo>();

	public FragmentItemHelper(Context context, FragmentManager manager,
			int containerId) {
		mContext = context;
		mManager = manager;
		mContainerId = containerId;
	}

	public void addItem(String _tag, Class<?> _class, Bundle _args) {
		FragmentInfo fragmentInfo = new FragmentInfo(_tag, _class, _args);
		mFragments.add(fragmentInfo);
	}

	public void setItemChanged(int p) {
		FragmentTransaction ft = doItemChanged(mFragments.get(p).tag, null);
		if (ft != null) {
			ft.commit();
		}
	}

	public String getItemText(int p){
		return mFragments.get(p).tag;
	}
	public void handleSaveInstanceState(Bundle outState) {
		outState.putString("tab", mCurrentTabTag);
	}

	private FragmentTransaction doItemChanged(String _tag,
			FragmentTransaction ft) {
		FragmentInfo mFragment = null;
		for (int i = 0; i < mFragments.size(); i++) {
			FragmentInfo fragment = mFragments.get(i);
			if (fragment.tag.equals(_tag)) {
				mFragment = fragment;
			}
		}
		if (mFragment == null) {
			throw new IllegalStateException("_tag ²»ÄÜÎª¿Õ " + _tag);
		}
		mCurrentTabTag = _tag;
		if (mLastFragment != mFragment) {
			if (ft == null) {
				ft = mManager.beginTransaction();
			}
			if (mLastFragment != null) {
				if (mLastFragment.fragment != null) {
					ft.detach(mLastFragment.fragment);
				}
			}
			if (mFragment != null) {
				if (mFragment.fragment == null) {
					mFragment.fragment = Fragment.instantiate(mContext,
							mFragment.clss.getName(), mFragment.args);
					ft.add(mContainerId, mFragment.fragment, mFragment.tag);
				} else {
					ft.attach(mFragment.fragment);
				}
			}

			mLastFragment = mFragment;
		}
		return ft;
	}

	public void handleDestroyView() {
		mFragments.clear();
	}

	public void handleViewStateRestored(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentTabTag = savedInstanceState.getString("tab");
		}

		String currentTab = mCurrentTabTag;

		FragmentTransaction ft = null;
		for (int i = 0; i < mFragments.size(); i++) {
			FragmentInfo tab = mFragments.get(i);
			tab.fragment = mManager.findFragmentByTag(tab.tag);
			if (tab.fragment != null && !tab.fragment.isDetached()) {
				if (tab.tag.equals(currentTab)) {
					mLastFragment = tab;
				} else {
					if (ft == null) {
						ft = mManager.beginTransaction();
					}
					ft.detach(tab.fragment);
				}
			}
		}

		ft = doItemChanged(currentTab, ft);
		if (ft != null) {
			ft.commit();
			mManager.executePendingTransactions();
		}
	}

	static final class FragmentInfo {
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		private Fragment fragment;

		FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
			tag = _tag;
			clss = _class;
			args = _args;
		}
	}
}