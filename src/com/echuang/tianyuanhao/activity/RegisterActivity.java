package com.echuang.tianyuanhao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.utils.RegexValidateUtil;

/**
 * 注册界面
 * 
 * @ClassName: RegisterActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 下午2:15:46
 */
public class RegisterActivity extends BaseSwipeBackActivity {
	private static final String TAG = "RegisterActivity";
	private Button bt_register;
	private TextView tv_read;
	private Button bt_refreashcode;
	private EditText et_username, et_password, et_code, et_invite;

	private ImageButton ib_scan;
	// 计时器 倒计时
	private TimeCount timeCount;

	private String username = "", password = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_register);
		initView();
	}

	@Override
	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	protected void next() {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 13 && resultCode == Activity.RESULT_OK
				&& data != null) {
		}
	}

	@Override
	protected void initView() {
		initTitle("注册", "", 0);
	}

	/**
	 * 登录方法
	 * 
	 * @Title: RegisterService
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void RegisterService() {
		// 获取输入的账户及密码
		if (!RegexValidateUtil.validate(password)) {
			toast("密码不能包含空格和中文!");
			return;
		}

	}

	protected void onPause() {
		super.onPause();
	}

	/**
	 * 倒计时的类
	 * 
	 */
	public class TimeCount extends CountDownTimer {
		// 倒计时文案
		private String mCountdown;

		int textSizeRuning;
		int textSizeStop;

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			mCountdown = getString(R.string.base_countdown_text);
			textSizeRuning = getResources().getDimensionPixelOffset(
					R.dimen.textsize_countdown);
			textSizeStop = getResources().getDimensionPixelOffset(
					R.dimen.textsize_content);
		}

		@Override
		public void onFinish() {
			bt_refreashcode.setText("获取验证码");
			bt_refreashcode.setTextSize(TypedValue.COMPLEX_UNIT_PX,
					textSizeStop);
			bt_refreashcode.setEnabled(true);
			bt_refreashcode
					.setBackgroundResource(R.drawable.button_selector_bluegray);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			bt_refreashcode.setEnabled(false);
			String text = String.format(mCountdown, millisUntilFinished / 1000);
			bt_refreashcode.setText(text);
			bt_refreashcode.setTextSize(TypedValue.COMPLEX_UNIT_PX,
					textSizeRuning);
			bt_refreashcode.setBackgroundResource(R.drawable.button_shape_gray);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

}
