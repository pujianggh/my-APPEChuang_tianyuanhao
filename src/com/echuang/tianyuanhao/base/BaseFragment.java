package com.echuang.tianyuanhao.base;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.utils.CustomToast;
import com.echuang.tianyuanhao.utils.SharedPreUtils;
import com.echuang.tianyuanhao.view.CustomProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragent基类
 * @ClassName: BaseFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午1:38:48
 */
public abstract class BaseFragment extends Fragment implements OnClickListener{
	abstract protected void initView();

	abstract protected void back();

	abstract protected void next();
	
	@Override
	public void onClick(View v) {
		
	}

	protected CustomProgressDialog dialog;
	protected SharedPreUtils sharedPreUtils;
	protected Intent intent;
	/**局部广播管理器**/
	protected LocalBroadcastManager broadcastManager;
	public boolean needAnimation = true;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreUtils = new SharedPreUtils(getActivity());
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		intent = getActivity().getIntent();
		if(null == intent){
			intent = new Intent();
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		activity = ((MainActivity) getActivity());
		getChildFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out);
				
			}
		});
		broadcastManager = LocalBroadcastManager.getInstance(getActivity());
	}

	protected void startDialog(String msg) {
		if (dialog == null) {
			dialog = CustomProgressDialog.createDialog(getActivity());
		}
		dialog.setMessage(msg);
		dialog.show();
		new TimeCount(TimeCount.TIMEOUT, 1000).start();
	}

	protected void startDialog() {
		if (dialog == null) {
			dialog = CustomProgressDialog.createDialog(getActivity());
		}
		dialog.setMessage("加载中...");
		dialog.show();
		new TimeCount(TimeCount.TIMEOUT, 1000).start();
	}

	protected void stopDialog() {
		try {
			if (dialog != null) {
				dialog.dismiss();
				dialog = null;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收起输入法
	 */
	protected void hideInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		if(getActivity().getCurrentFocus() != null){
			inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	/**
	 * toast
	 * @param msg
	 */
	protected void toast(String msg) {
		if("".equals(msg)){
			return;
		}
		try {
			if(!getActivity().isFinishing()){
				CustomToast.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * toast
	 * @param msg
	 */
	protected void toast(int msg) {
		try {
			if(!getActivity().isFinishing()){
				CustomToast.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** loading界面 **/
	private LinearLayout ll_loading;
	private TextView tv_loading;

	/** 初始化loadingview **/
	protected void initLoading(View view) {
		if (view == null) {
			return;
		}
		ll_loading = (LinearLayout) view.findViewById(R.id.layout_loading);
		tv_loading = (TextView) view.findViewById(R.id.textView_loading);
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
		if(tv_loading != null){
			tv_loading.setText("请检查您的网络设置\n或点击页面刷新");
			tv_loading.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.error404), null, null);
		}
	}
	protected void showLoadingViewNoData() {
		if (ll_loading != null) {
			ll_loading.setVisibility(View.VISIBLE);
		}
		if(tv_loading != null){
			tv_loading.setText("");
			tv_loading.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.norecord), null, null);
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
	
	
	@Override
	public  void startActivity(Intent intent) {
		super.startActivity(intent);
		if(needAnimation){
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		if(needAnimation){
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}
	/*******************************还不太清楚具体是干什么的******************************************************/
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
}
