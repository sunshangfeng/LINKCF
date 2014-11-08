package com.ssf.linkcf.helper;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class NetWorkPostHelper {

	private final static String URL = "http://www.linkcf.net/interface.php";

	private final static String STATUS = "Status";

	private OnNetWorkListener mNetWorkListener;

	private RequestQueue mQueue;

	private JsonObjectRequest mObjectRequest;

	public NetWorkPostHelper(Context context) {
		mQueue = Volley.newRequestQueue(context);
	}

	/**
	 * ��ʼ��������
	 */
	public void startNetWork(Map<String, Object> map) {

		VolleyLog.d("����� =[%s]", new JSONObject(map).toString());
		
		mObjectRequest = new JsonObjectRequest(Method.POST, URL,
				new JSONObject(map), new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject arg0) {
						VolleyLog.d("��ȷ���ص�=[%s]", arg0.toString());
						try {
							if (arg0.getString(STATUS).equals("1")&& mNetWorkListener != null) {
								mNetWorkListener.onResponse(arg0.get("Data"));
							}else {
								mNetWorkListener.onErrorResponse(arg0.getString("Msg"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
							mNetWorkListener.onErrorResponse(null);
							VolleyLog.d("����json�쳣��=[%s]", e.getMessage());
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (mNetWorkListener != null) {
							mNetWorkListener.onErrorResponse(arg0.getMessage());
							VolleyLog.d("���󷵻ص�=[%s]",arg0.getMessage());
						}
					}
				});

		if (mQueue != null) {
			mQueue.add(mObjectRequest);
		}
	}

	/**
	 * �����������
	 * 
	 * @param netWorkListener
	 *            ��������ӿ�
	 */
	public void setOnNetWorkListener(OnNetWorkListener netWorkListener) {
		mNetWorkListener = netWorkListener;
	}

	/**
	 * ȡ����������
	 */
	public void cancel() {
		if (mObjectRequest != null && !mObjectRequest.isCanceled()) {
			mObjectRequest.cancel();
		}
	}

	/**
	 * ��������ӿ�
	 * @author sunshangfeng
	 *
	 */
	public interface OnNetWorkListener {

		void onResponse(Object arg0);

		void onErrorResponse(String arg0);
	}

}
