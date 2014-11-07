package com.ssf.linkcf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ssf.linkcf.bean.LoginBean;
import com.ssf.linkcf.helper.ComplexPreferencesHelper;
import com.ssf.linkcf.helper.Constant.Preferences;

public class WelcomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		ComplexPreferencesHelper complexPreferences = ComplexPreferencesHelper.getComplexPreferences(this, Preferences.MYPREF, MODE_PRIVATE);
		LoginBean user = complexPreferences.getObject(Preferences.NAME, LoginBean.class);
		
		if (user != null) {
			startActivity(new Intent(this, MainActivity.class)
			.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK));
			
		}
		
		findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
			}
		});
		findViewById(R.id.btn_register).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WelcomeActivity.this, RegisteredActivity.class));
			}
		});
	}

	
	
}
