package com.ssf.linkcf;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ssf.linkcf.bean.LoginBean;
import com.ssf.linkcf.helper.ComplexPreferencesHelper;
import com.ssf.linkcf.helper.Constant.Preferences;
import com.ssf.linkcf.helper.NetWorkPostHelper;
import com.ssf.linkcf.helper.NetWorkPostHelper.OnNetWorkListener;

public class LoginActivity extends BaseActivity implements OnNetWorkListener {

	private Button mLoginBt;

	private EditText mUserName, mPassWord;

	private NetWorkPostHelper mNetWork;

	private Context mContext;

	private ProgressDialog mProgress;
	
	private String username,password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		setContentView(R.layout.activity_login);

		mUserName = (EditText) findViewById(R.id.text_username);
		mPassWord = (EditText) findViewById(R.id.text_password);
		mLoginBt = (Button) findViewById(R.id.btn_login_main);

		mUserName.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEditTextNull(s.length(), mPassWord);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mPassWord.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEditTextNull(s.length(), mUserName);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mNetWork = new NetWorkPostHelper(this);

		mNetWork.setOnNetWorkListener(this);

		mLoginBt.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				username = mUserName.getText().toString();
				password = mPassWord.getText().toString();
				Map<String, Object> map = new HashMap<>();
				map.put("Method", "login.login");
				map.put("UserName", username);
				map.put("Password", password);
				mProgress = ProgressDialog.show(mContext, null, "正在登陆..");
				mNetWork.startNetWork(map);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mNetWork != null) {
			mNetWork.cancel();
		}
	}

	@Override
	public void onResponse(Object arg0) {
		mProgress.dismiss();
		LoginBean loginBean = new Gson().fromJson(arg0.toString(), LoginBean.class);
		loginBean.setUserName(username);
		loginBean.setPassword(password);
		ComplexPreferencesHelper complexPreferences = ComplexPreferencesHelper
				.getComplexPreferences(this, Preferences.MYPREF, MODE_PRIVATE);
		;
		complexPreferences.putObject(Preferences.NAME, loginBean);
		complexPreferences.commit();
		startActivity(new Intent(mContext, MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	@Override
	public void onErrorResponse(String arg0) {
		mProgress.dismiss();
		if (!arg0.isEmpty()) {
			Toast.makeText(mContext, arg0, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 验证 用户和密码是不是空
	 * 
	 * @param len0
	 * @param arg
	 */
	private void isEditTextNull(int len0, EditText arg) {
		if (len0 > 0 && !arg.getText().toString().isEmpty()) {
			if (!mLoginBt.isEnabled()) {
				mLoginBt.setEnabled(true);
			}
		} else {
			mLoginBt.setEnabled(false);
		}
	}
}
