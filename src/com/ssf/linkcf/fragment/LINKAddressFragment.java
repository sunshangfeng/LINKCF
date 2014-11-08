package com.ssf.linkcf.fragment;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.ssf.linkcf.MainActivity;
import com.ssf.linkcf.R;
import com.ssf.linkcf.bean.AddressBean;
import com.ssf.linkcf.bean.LoginBean;
import com.ssf.linkcf.helper.ComplexPreferencesHelper;
import com.ssf.linkcf.helper.Constant.Preferences;
import com.ssf.linkcf.helper.NetWorkPostHelper;
import com.ssf.linkcf.helper.NetWorkPostHelper.OnNetWorkListener;

public class LINKAddressFragment extends ProgressListFragment implements OnNetWorkListener {

	private NetWorkPostHelper mNetWork;
	
	private ComplexPreferencesHelper mComplexPreferences;

	private LoginBean loginBean;

    public static LINKAddressFragment newInstance() {
    	LINKAddressFragment fragment = new LINKAddressFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);
        
        mNetWork = new NetWorkPostHelper(getActivity());

		mNetWork.setOnNetWorkListener(this);
        
		mComplexPreferences =ComplexPreferencesHelper.getComplexPreferences(getActivity(),Preferences.MYPREF, Context.MODE_PRIVATE);
		
		loginBean = mComplexPreferences.getObject(Preferences.NAME, LoginBean.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getListAdapter() == null) {
        	obtainData();
		}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mHandler.removeCallbacks(mShowContentRunnable);
        if (mNetWork != null) {
			mNetWork.cancel();
		}
    }

    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MainActivity activity = (MainActivity) getActivity();
		if (!activity.isDrawerOpen()) {
			activity.restoreActionBar();
			inflater.inflate(R.menu.refresh, menu);
		}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                obtainData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obtainData() {
        // Show indeterminate progress
        setListShown(false);
        Map<String, Object> map = new HashMap<>();
		map.put("Method", "address. getlist");
		map.put("UserID",loginBean.getUserID());
		map.put("AccessToken", loginBean.getAccessToken());
    	mNetWork.startNetWork(map);
    }

	@Override
	public void onResponse(Object arg0) {
		AddressBean addressBean = new Gson().fromJson(arg0.toString(), AddressBean.class);
		setEmptyText(addressBean.toString());
		setListShown(true);
	}

	@Override
	public void onErrorResponse(String arg0) {
		// TODO Auto-generated method stub
		setEmptyText(arg0.toString());
		setListShown(true);
	}
}
