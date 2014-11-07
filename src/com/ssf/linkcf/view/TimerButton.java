package com.ssf.linkcf.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TimerButton extends Button {

	public TimerButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public TimerButton(Context context) {
        super(context);
 
    }
    public static String target = "1重新获取";
    public static String tempTarget = target;
    private int time = 30;
    private Handler ajaxHandler;
    private final Handler mainH = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Log.e("msg.what", msg.what + "");
            if (msg.what >0) {
                target = replaceStr(tempTarget, time + "S", "(\\d{1,2})");
                sendEmptyMessageDelayed(time--, 1000);
                setText(target + "");
                Log.e("time", time + "");
            } else if (msg.what == 0) {
            	setEnabled(true);
            	 setText("获取验证码");
                if (null != getAjaxHandler())
                    getAjaxHandler().sendEmptyMessage(time);
            }
 
        };
    };
    
    
    /**
     * Capture mouse press events to update text state.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TextOnlyButton", event.getAction() + "");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
        	if (isEnabled()) {
        		setEnabled(false);
        		setTime(30);
        		invalidate();
			}
        }
        return super.onTouchEvent(event);
    }
    
    public static String replaceStr(String target, String reString, String regex) {
        if (null == reString)
            return target;
        reString = reString.trim();
        if ("".equals(reString))
            return target;
        String[] strs = reString.split(",");
        int len = strs.length;
 
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(target);
 
        int i = 0;
        while (matcher.find()) {
            if (i < len)
                target = target.replace(matcher.group(0), strs[i++]);
 
        }
        return target;
    }
    
    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }
 
    /**
     * @param time
     *            the time to set
     */
    public void setTime(int time) {
        this.time = time;
        mainH.sendEmptyMessage(time);
    }
 
    /**
     * @return the ajaxHandler
     */
    public Handler getAjaxHandler() {
        return ajaxHandler;
    }
 
    /**
     * @param ajaxHandler
     *            the ajaxHandler to set
     */
    public void setAjaxHandler(Handler ajaxHandler) {
        this.ajaxHandler = ajaxHandler;
    }
 
    /**
     * @return the mainH
     */
    public Handler getMainH() {
        return this.mainH;
    }

}
