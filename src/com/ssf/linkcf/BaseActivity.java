package com.ssf.linkcf;

import com.ssf.linkcf.view.DrawerArrowDrawable;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseActivity extends Activity {

	private DrawerArrowDrawable drawerArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		drawerArrow.setProgress(1.f); /**设置返回键方向**/
		getActionBar().setHomeAsUpIndicator(drawerArrow);
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        // TODO Auto-generated method stub
	        if(item.getItemId() == android.R.id.home)
	        {
	            finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
}
