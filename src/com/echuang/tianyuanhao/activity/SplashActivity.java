package com.echuang.tianyuanhao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.gesture.GestureVerifyActivity;
import com.echuang.tianyuanhao.utils.SharedPreUtils;

/**
 * 启动页
 * 
 * @ClassName: SplashActivity
 * @Description: TODO 程序启动Activity
 * @author 蒲江
 * @date 2016-7-11 下午2:01:23
 */
public class SplashActivity extends BaseFragmentActivity {

	private ImageView startImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_splash);
		initView();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3 * 1000);
					next();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	@Override
	protected void back() {
	}

	@Override
	protected void next() {
		boolean login = SharedPreUtils.getInstance(this).getIsLogin();
		// 是否存在手势密码
		boolean gesture = SharedPreUtils.getInstance(this).getHasGesture();
		// 获取设置里手势密码开关状态
		boolean canUseGesture = SharedPreUtils.getInstance(this)
				.getGestureSwitch();
		
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
//		if (login) {
//			if (gesture && canUseGesture) {
//				// 手势密码验证
//				startVerifyLockPattern();
//			} else {
//				Intent intent = new Intent(SplashActivity.this,
//						MainActivity.class);
//				startActivity(intent);
//			}
//		} else if (SharedPreUtils.getInstance(this).getFirstEnter()) {
//			// 首次启动引导页
//			Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
//			startActivity(intent);
//		} else {
//			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//			startActivity(intent);
//		}
		SplashActivity.this.finish();
	}

	/**
	 * 进入手势密码界面
	 * @Title: startVerifyLockPattern
	 * @Description: TODO 
	 * @param 
	 * @return void
	 * @throws
	 */
	private void startVerifyLockPattern() {
		Intent intent = new Intent(this, GestureVerifyActivity.class);
		startActivity(intent);
	}

	@Override
	protected void initView() {
		startImage = (ImageView) findViewById(R.id.picture);
		startImage.setImageDrawable(getResources().getDrawable(R.drawable.default_splash_img));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
