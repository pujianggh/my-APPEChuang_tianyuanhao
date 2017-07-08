package com.echuang.tianyuanhao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.dialog.TipsDialog;
import com.echuang.tianyuanhao.gesture.GestureEditActivity;
import com.echuang.tianyuanhao.gesture.GestureVerifyActivity;
import com.echuang.tianyuanhao.utils.SharedPreUtils;
import com.echuang.tianyuanhao.view.switchview.SwitchButton;

/**
 * 系统设置
 * 
 * @ClassName: SystemSetActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-19 上午9:16:58
 */
public class SystemSetActivity extends BaseSwipeBackActivity implements
		OnClickListener {
	private boolean canChangeGesture = false;// 手势密码记录开关
	private TipsDialog mTipsDialog;
	private Button mExitSystem;
	private SwitchButton mGesturePassword;// 手势密码开关

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_system_set);
		initView();
	}

	@Override
	protected void onResume() {
//		if (!SharedPreUtils.getInstance(this).getHasGesture()) {
//			canChangeGesture = true;
//		} else {
//			canChangeGesture = false;
//		}
//		mGesturePassword.setChecked(canChangeGesture);
		super.onResume();
	}

	@Override
	protected void initView() {
		initTitle("系统设置", "", 0);
		mExitSystem = (Button) findViewById(R.id.btn_exit_system);
		mGesturePassword = (SwitchButton) findViewById(R.id.sb_gesture_password);
		mExitSystem.setOnClickListener(this);
		mGesturePassword
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
//						if (isChecked) {
//							if (canChangeGesture) {
//								startSetLockPattern();
//							}
//						} else {
//							if (canChangeGesture) {
//								startVerifyLockPattern();
//							}
//						}
					}
				});
	}

	/**
	 * 设置手势密码
	 * 
	 * @Title: startSetLockPattern
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void startSetLockPattern() {
		Intent intent = new Intent(this, GestureEditActivity.class);
		intent.putExtra("isModify", true);
		startActivity(intent);
	}

	/**
	 * 验证手势密码
	 * 
	 * @Title: startVerifyLockPattern
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void startVerifyLockPattern() {
		// 如果不存在手势密码，直接关闭开关，无需先验证
		if (!SharedPreUtils.getInstance(this).getHasGesture()) {
			// SharedPreUtils.getInstance(this).saveGestureSwitch(false);
			upLoadGestureStatue();
			return;
		}
		Intent intent = new Intent(this, GestureVerifyActivity.class);
		intent.putExtra("need_verify", true);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_exit_system:
			mTipsDialog = new TipsDialog(this, "提示", "你确认退出天元号?",
					"取消", "确定", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							//确定退出
							mTipsDialog.dismiss();
						}
					});
			mTipsDialog.show();
			break;
		case R.id.imageButton_left:
			back();
			break;
		}
	}

	/**
	 * 将开关手势密码参数上传服务器
	 * 
	 * @Title: upLoadGestureStatue
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void upLoadGestureStatue() {
		SharedPreUtils.getInstance(this).saveGestureSwitch(false);
		// String url = UrlManage.BASE_URL;
		// String message;
		// Map<String, String> map = new HashMap<String, String>();
		// map.put(StrRes.id, SharedPreUtils.getInstance(this).getUserId());
		// map.put("isOpenGesturesPassword", "0");
		// message = Utils.addParams(map);
		// RequestParams params = NetUtils.getInstance().getParams(url,
		// message);
		// NetUtils.getInstance().post(this, url, params, new HttpCallback() {
		// @Override
		// public void onSuccess(String content) {
		// if (DecodeJsonManage.isSuccess(content)) {
		// SharedPreUtils.getInstance(SettingMainActivity.this)
		// .saveGestureSwitch(false);
		// }
		// }
		//
		// @Override
		// public void onFailure(String content, int statusCode) {
		//
		// }
		// });
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	protected void next() {
	}

}