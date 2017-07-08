package com.echuang.tianyuanhao.base;

import java.util.Date;
import java.util.List;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.utils.CustomToast;
import com.echuang.tianyuanhao.utils.SharedPreUtils;
import com.echuang.tianyuanhao.utils.Utils;
import com.echuang.tianyuanhao.view.CustomProgressDialog;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * BaseFragmentActivity基类
 * @ClassName: BaseFragmentActivity
 * @Description: TODO 方便于继承
 * @author 蒲江
 * @date 2016-7-8 下午1:44:34
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener{
	protected LinearLayout ll_title;
	protected ImageButton ib_left;
	protected Button bt_right;
	protected ImageButton ib_right;
	protected TextView tv_title;
	protected CustomProgressDialog dialog;
	protected Intent intent;
	protected SharedPreUtils sharedPreUtils;
	private View line;
	/**局部广播管理器**/
	protected LocalBroadcastManager broadcastManager;
	public boolean needAnimation = true;
	/**组件初始化**/
	abstract protected void initView();
	/**下一步或者下一个动作**/
	abstract protected void next();
	/**返回**/
	abstract protected void back();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setFontScale(this);
		AppAppliction.getInstance().addActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		sharedPreUtils = new SharedPreUtils(this);
		intent = getIntent();
		if(null == intent){
			intent = new Intent();
		}
		broadcastManager = LocalBroadcastManager.getInstance(this);
	}
	@Override
	public void onClick(View v) {
		
		
	}
	
	

	/**
	 * 初始化标题
	 */
	protected void initTitle(CharSequence title, CharSequence right, int type,
			int rightRes) {
		ll_title = (LinearLayout) findViewById(R.id.layout_title);
		tv_title = (TextView) findViewById(R.id.textView_title);
		ib_left= (ImageButton) findViewById(R.id.imageButton_left);
		ib_right = (ImageButton) findViewById(R.id.imageButton_right);
		bt_right = (Button) findViewById(R.id.button_right);
		line = (View) findViewById(R.id.line);
		if (null == ll_title && null == tv_title && null == ib_left
				&& null == ib_right) {
			return;
		}
		if (type == 1 && bt_right != null) {
			bt_right.setText(right);
			bt_right.setVisibility(View.VISIBLE);
			bt_right.setOnClickListener(this);
		} else if (type == 0 && ib_right != null) {
			ib_right.setVisibility(View.INVISIBLE);
			ib_right.setImageResource(R.drawable.button_back);
			if (rightRes != 0) {
				ib_right.setImageResource(rightRes);
				ib_right.setVisibility(View.VISIBLE);
			}
			ib_right.setOnClickListener(this);
		}
		ib_left.setVisibility(View.VISIBLE);
		ib_left.setImageResource(R.drawable.button_back);

		// ll_title.setBackgroundResource(R.color.trans);
		// tv_title.setTextColor(getResources().getColor(R.color.text_deepgray));
		tv_title.setText(title);

		ib_left.setOnClickListener(this);
	}
	
	//不需要的设为0
	protected void setColorBackGround(int bgColor, int txColor, int lineColor){
		if(bgColor!=0)
			ll_title.setBackgroundColor(bgColor);
		if(txColor!=0)
			tv_title.setTextColor(txColor);
		if(lineColor!=0)
			line.setBackgroundColor(lineColor);
	}
	
	protected void setImageBackGround(int drawable, int txColor, int lineColor){
		ll_title.setBackgroundResource(drawable);
		if(txColor!=0)
			tv_title.setTextColor(txColor);
		if(lineColor!=0)
			line.setBackgroundColor(lineColor);
	}
	
	protected void setLeftButton(int resId){
		ib_left.setImageResource(resId);
	}
	
	protected void setTitleText(String str){
		tv_title.setText(str);
	}

	protected void initTitle(int title, int right, int type) {
		initTitle(getResources().getString(title),
				getResources().getString(right), type,0);
	}

	protected void initTitle(CharSequence title, int right, int type) {
		initTitle(title, getResources().getString(right), type,0);
	}

	protected void initTitle(int title, CharSequence right, int type) {
		initTitle(getResources().getString(title), right, type,0);
	}
	
	protected void initTitle(CharSequence title, CharSequence right, int type) {
		initTitle(title, right, type,0);
	}
	protected void initTitle(int title, CharSequence right, int type,int res) {
		initTitle(getResources().getString(title), right, type,res);
	}
	protected void initTitle(CharSequence title, int right, int type, int res) {
		initTitle(title, getResources().getString(right), type, res);
	}
	protected void initTitle(int title, int right, int type,int res) {
		initTitle(getResources().getString(title), getResources().getString(right), type,res);
	}

	protected void startDialog(String msg) {
		if (dialog == null) {
			dialog = CustomProgressDialog.createDialog(this);
		}
		dialog.setMessage(msg);
		dialog.show();
		new TimeCount(TimeCount.TIMEOUT, 1000).start();
	}

	protected void startDialog() {
		if (dialog == null) {
			dialog = CustomProgressDialog.createDialog(this);
		}
		dialog.setMessage("加载中");
		dialog.show();
		new TimeCount(TimeCount.TIMEOUT, 1000).start();
	}

	protected void stopDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * 收起输入法
	 */
	protected void hideInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(this.getCurrentFocus() != null){
			inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	/**
	 * toast
	 * @param msg
	 */
	public void toast(String msg) {
		if("".equals(msg)){
			return;
		}
		try {
			CustomToast.showToast(this, msg, Toast.LENGTH_SHORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * toast
	 * @param msg
	 */
	public void toast(int msg) {
		try {
			CustomToast.showToast(this, msg, Toast.LENGTH_SHORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** loading界面 **/
	private LinearLayout ll_loading;

	/** 初始化loadingview **/
	protected void initLoading() {
		ll_loading = (LinearLayout) findViewById(R.id.layout_loading);
		if (ll_loading != null) {
			ll_loading.setVisibility(View.GONE);
			ll_loading.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Retry();
				}
			});
		}
	}

	/**
	 * 显示loading
	 * 
	 * @param isLoading
	 *            是否是loading
	 */
	protected void showLoadingView(boolean isLoading) {
		if (ll_loading != null) {
			ll_loading.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏loading
	 */
	protected void hideLoadingView() {
		if (ll_loading != null) {
			ll_loading.setVisibility(View.GONE);
		}
	}

	/** 重试 **/
	protected void Retry() {
	};
	public class TimeCount extends CountDownTimer {
		public static final long TIMEOUT = 30000;
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			stopDialog();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}

	}
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		if(needAnimation){
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if(needAnimation){
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			Thread.sleep(1*1000);
			new Thread(){
				public void run() {
					boolean isCurrentRunningForeground=isRunningForeground();
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					Editor editor=prefs.edit();  
					editor.putBoolean("isCurrentRunningForeground", isCurrentRunningForeground);
					editor.putLong("time", getCurrentTime());
					editor.commit(); 
				};
			}.start();
		} catch (Exception e) {
			
		}
	}
	
	private long getCurrentTime(){
		Date curDate = new Date(System.currentTimeMillis());
		long time = curDate.getTime();
		return time;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		boolean isCurrentRunningForeground = prefs.getBoolean("isCurrentRunningForeground", false);
		long cur_time = getCurrentTime();
		long end_time = prefs.getLong("time", cur_time);
		if (!isCurrentRunningForeground && ((cur_time-end_time)>2*60*1000 || (cur_time-end_time)<0)) {
			//从后台回来
			//是否登录
			boolean login = SharedPreUtils.getInstance(this).getIsLogin();
			//是否存在手势密码
			boolean gesture = SharedPreUtils.getInstance(this).getHasGesture();
			//获取设置里手势密码开关状态
		    boolean canUseGesture = SharedPreUtils.getInstance(this).getGestureSwitch();
			if(login){
				if(gesture && canUseGesture){
					//手势密码验证
					//startVerifyLockPattern();
				}
		    }
		}
	}
	
	public boolean isRunningForeground(){
		String packageName=getPackageName(this);
		String topActivityClassName=getTopActivityName(this);
		System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
		if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public  String getTopActivityName(Context context){
		String topActivityClassName=null;
		 ActivityManager activityManager =
		(ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
	     List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
	     if(runningTaskInfos != null){
	    	 ComponentName f=runningTaskInfos.get(0).topActivity;
	    	 topActivityClassName=f.getClassName();
	    	
	     }
	     return topActivityClassName;
	}
	
	public String getPackageName(Context context){
		 String packageName = context.getPackageName();  
		 return packageName;
	}
	
}
