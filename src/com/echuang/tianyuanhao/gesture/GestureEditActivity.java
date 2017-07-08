package com.echuang.tianyuanhao.gesture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.gesture.GestureDrawline.GestureCallBack;
import com.echuang.tianyuanhao.utils.SharedPreUtils;
import com.echuang.tianyuanhao.utils.Utils;

/**
 * 密码设置界面
 * 
 * @ClassName: GestureEditActivity
 * @Description: TODO 手势密码设置界面
 * @author 蒲江
 * @date 2016-7-11 下午2:39:12
 */
public class GestureEditActivity extends Activity implements OnClickListener {
	/** 手机号码 */
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	/** 首次提示绘制手势密码，可以选择跳过 */
	public static final String PARAM_IS_FIRST_ADVICE = "PARAM_IS_FIRST_ADVICE";
	private TextView mTextTitle;
	private TextView mTextCancel;
	private LockIndicator mLockIndicator;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextReset;
	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;
	private TextView mTextUserPhone;
	private boolean isModify = false;// 记录

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_gesture_edit);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		isModify = false;
		Intent it = getIntent();
		if (it != null) {
			isModify = it.getBooleanExtra("isModify", false);
		}
		setUpViews();
		setUpListeners();
	}

	private void setUpViews() {
		String userName = null;
		mTextTitle = (TextView) findViewById(R.id.include_text_title);
		mTextCancel = (TextView) findViewById(R.id.cancel);
		mTextReset = (TextView) findViewById(R.id.text_reset);
		mTextReset.setVisibility(View.INVISIBLE);
		mTextReset.setClickable(false);
		mTextUserPhone = (TextView) findViewById(R.id.text_user_phone);
		userName = SharedPreUtils.getInstance(this).getUserName();
		if (userName != null && Utils.isNumeric(userName)
				&& userName.length() == 11) {
			userName = userName.substring(0, 3) + "****"
					+ userName.substring(userName.length() - 4);
		} else if (userName != null && userName.length() >= 3) {
			userName = userName.substring(0, 2) + "***"
					+ userName.substring(userName.length() - 2);
		} else {
			userName = "";
		}
		mTextUserPhone.setText(userName);
		mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "",
				new GestureCallBack() {
					@Override
					public void onGestureCodeInput(String inputCode) {
						mTextReset.setVisibility(View.VISIBLE);
						if (!isInputPassValidate(inputCode)) {
							mTextTip.setText("最少连接4个点");
							mGestureContentView.clearDrawlineState(0L);
							return;
						}
						if (mIsFirstInput) {
							mTextTip.setText(R.string.setup_gesture_pattern_again);
							mFirstPassword = inputCode;
							updateCodeList(inputCode);
							mGestureContentView.clearDrawlineState(0L);
							mTextReset.setClickable(true);
							mTextReset
									.setText(getString(R.string.set_gesture_retry));
						} else {
							if (inputCode.equals(mFirstPassword)) {
								mGestureContentView.clearDrawlineState(0L);
								upLoadGesturePassword();
								GestureEditActivity.this.finish();
							} else {
								mTextTip.setText("两次绘制不一样");
								// 左右移动动画
								Animation shakeAnimation = AnimationUtils
										.loadAnimation(
												GestureEditActivity.this,
												R.anim.shake);
								mTextTip.startAnimation(shakeAnimation);
								// 保持绘制的线，1.5秒后清除
								mGestureContentView.clearDrawlineState(1300L);
							}
						}
						mIsFirstInput = false;
					}

					@Override
					public void checkedSuccess() {

					}

					@Override
					public void checkedFail() {

					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		updateCodeList("");
	}

	// 上传手势密码到服务器
	private void upLoadGesturePassword() {
	}

	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextReset.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void updateCodeList(String inputCode) {
		// 更新选择的图案
		mLockIndicator.setPath(inputCode);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			// SharedPreUtils.getInstance(this).saveGesture(false);
			this.finish();
			break;
		case R.id.text_reset:
			mIsFirstInput = true;
			updateCodeList("");
			mTextReset.setVisibility(View.INVISIBLE);
			mTextTip.setText(getString(R.string.set_gesture_pattern));
			break;
		default:
			break;
		}
	}

	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}

}
