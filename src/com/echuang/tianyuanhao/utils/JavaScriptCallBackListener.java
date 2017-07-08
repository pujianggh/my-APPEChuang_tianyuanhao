package com.echuang.tianyuanhao.utils;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.echuang.tianyuanhao.activity.CommonWebActivity;
import com.echuang.tianyuanhao.activity.LoginActivity;
import com.echuang.tianyuanhao.activity.MainActivity;
import com.echuang.tianyuanhao.utils.JavaScriptInterface.CallBackListener;
import com.loopj.android.http.RequestParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 回调监听类
 * 
 * @ClassName: JavaScriptCallBackListener
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 下午4:16:12
 */
public class JavaScriptCallBackListener implements CallBackListener {
	private Context mContext;
	private boolean isMsg = false;
	private String msg = "";
	private int type = -1;

	public JavaScriptCallBackListener(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public JavaScriptCallBackListener(Context mContext, boolean isMsg) {
		super();
		this.isMsg = isMsg;
		this.mContext = mContext;
	}

	@Override
	public void callBack(String msg, int type) {
		this.msg = "";
		this.type = -1;
		// 需不需要登录，在此判断
		switch (type) {
		case JavaScriptType.HTML:
			mContext.startActivity(new Intent(mContext,
					CommonWebActivity.class).putExtra(StrRes.url, msg)
					.putExtra(StrRes.title, msg)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			break;
		case JavaScriptType.SHOUYE:// 首页
			mContext.startActivity(new Intent(mContext, MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			break;
		}
	}

}
