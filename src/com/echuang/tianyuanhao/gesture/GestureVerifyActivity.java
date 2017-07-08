package com.echuang.tianyuanhao.gesture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.LoginActivity;
import com.echuang.tianyuanhao.activity.MainActivity;
import com.echuang.tianyuanhao.gesture.GestureDrawline.GestureCallBack;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.SharedPreUtils;
import com.echuang.tianyuanhao.utils.Utils;

/**
 * 手势验证界面
 * 
 * @ClassName: GestureVerifyActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 下午2:36:50
 */
public class GestureVerifyActivity extends Activity implements
		android.view.View.OnClickListener {
	/** 手机号码 */
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	private RelativeLayout mTopLayout;
	private TextView mTextTitle;
	private TextView mTextCancel;
	// private ImageView mImgUserLogo;
	private TextView mTextPhoneNumber;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextForget;
	private TextView mTextOther;
	private String mParamPhoneNumber;
	private long mExitTime = 0;
	private int mParamIntentCode;
	private String mPassword = "";
	private int wrongNum = 5;
	boolean startMain; // 一般情况下，进入APP之后进入首页
	boolean closeGesture; // 设置里, 关闭手势密码开关后先验证
	boolean modifyGesture; // 设置里，修改手势密码，先验证，再修改

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_gesture_verify);
		startMain = true;
		closeGesture = false;
		modifyGesture = false;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Intent intent = getIntent();
		if (intent != null) {
			startMain = intent.getBooleanExtra("start_main", true);
			closeGesture = intent.getBooleanExtra("need_verify", false);
			modifyGesture = intent.getBooleanExtra("modify", false);
			LogAPP.i("pj", "closeGesture= " + closeGesture);
			LogAPP.i("pj", "modifyGesture= " + modifyGesture);
		}
		ObtainExtraData();
		setUpViews();
		setUpListeners();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void ObtainExtraData() {
		mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
		mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
	}

	private void setUpViews() {
		String userName = null;
		mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
		mTextTitle = (TextView) findViewById(R.id.include_text_title);
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		// mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
		mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
		userName = SharedPreUtils.getInstance(this).getUserName();
		if (userName != null && Utils.isNumeric(userName)
				&& userName.length() == 11) {
			userName = userName.substring(0, 3) + "****"
					+ userName.substring(userName.length() - 4);
		} else if (userName != null) {
			userName = userName.substring(0, 2) + "***"
					+ userName.substring(userName.length() - 2);
		} else {
			userName = "";
		}
		mTextPhoneNumber.setText(userName + ", 欢迎回来！");
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mTextTip.setText("请输入锁屏密码");
		if (modifyGesture || closeGesture) {
			mTextTip.setText("请输入原锁屏密码");
		}
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
		mTextOther = (TextView) findViewById(R.id.text_other_account);

		if (closeGesture || modifyGesture) {
			mTextOther.setText("取消");
		}

		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "",
				new GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {
						// 从服务器验证密码
					}

					@Override
					public void checkedSuccess() {
						LogAPP.i("pj", "checkedSuccess");
						success();
					}

					@Override
					public void checkedFail() {
						fail();
					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
	}

	private void success() {
		mGestureContentView.clearDrawlineState(0L);
		// 进入首页
		LogAPP.i("pj", "modifyGesture=  " + modifyGesture);
		if (closeGesture) {
			// SharedPreUtils.getInstance(GestureVerifyActivity.this).saveGestureSwitch(false);
		} else if (modifyGesture) {
			LogAPP.i("pj", "modifyGesture");
			Intent intent = new Intent(GestureVerifyActivity.this,
					GestureEditActivity.class);
			intent.putExtra("isModify", true);
			startActivity(intent);
			GestureVerifyActivity.this.finish();
		} else if (startMain) {
			startActivity(new Intent(GestureVerifyActivity.this,
					MainActivity.class));
			GestureVerifyActivity.this.finish();
		} else {
			GestureVerifyActivity.this.finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			if (!closeGesture && !modifyGesture && !startMain) {
				return false;
			}
		}
		LogAPP.i("pj", "onKeyDown = " + super.onKeyDown(keyCode, event));
		return super.onKeyDown(keyCode, event);
	}

	private void fail() {
		wrongNum--;
		if (wrongNum <= 0) {
			// 连续5次手势密码输入错误，自动跳转到登录页面
			// Utils.logOut(this); //登出账号
			SharedPreUtils.getInstance(GestureVerifyActivity.this).saveGesture(
					false);
			SharedPreUtils.getInstance(GestureVerifyActivity.this).saveIsLogin(
					false);
			Intent intent = new Intent(GestureVerifyActivity.this,
					LoginActivity.class);
			intent.putExtra("forgetGesture", true);
			startActivity(intent);
			finish();
			return;
		}
		mGestureContentView.clearDrawlineState(1300L);
		mTextTip.setVisibility(View.VISIBLE);
		mTextTip.setText("密码错误，您还能输入" + wrongNum + "次");
		// 左右移动动画
		Animation shakeAnimation = AnimationUtils.loadAnimation(
				GestureVerifyActivity.this, R.anim.shake);
		mTextTip.startAnimation(shakeAnimation);
	}

	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextForget.setOnClickListener(this);
		mTextOther.setOnClickListener(this);
	}

	private String getProtectedMobile(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(phoneNumber.subSequence(0, 3));
		builder.append("****");
		builder.append(phoneNumber.subSequence(7, 11));
		return builder.toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_cancel:
			this.finish();
			break;
		case R.id.text_forget_gesture:
			break;
		case R.id.text_other_account:
			if (closeGesture || modifyGesture) {
				this.finish();
				return;
			}
			// 自动登出当前的登录状态，然后跳登陆页
			SharedPreUtils.getInstance(this).saveIsLogin(false);
			SharedPreUtils.getInstance(this).saveUserId("");
			// 清除手势
			SharedPreUtils.getInstance(this).saveGesture(false);
			finish();

			Intent intent = new Intent(GestureVerifyActivity.this,
					LoginActivity.class);
			intent.putExtra("fromGesture", true);
			startActivity(intent);
			// broadcastManager.sendBroadcast(new Intent(MainActivity.LOGOUT));
			break;
		default:
			break;
		}
	}
}
