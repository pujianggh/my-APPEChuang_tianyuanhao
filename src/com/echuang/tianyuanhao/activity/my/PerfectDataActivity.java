package com.echuang.tianyuanhao.activity.my;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;

/**
 * 完善资料-身份认证
 * 
 * @ClassName: PerfectDataActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-8-31 上午9:16:45
 */
public class PerfectDataActivity extends BaseSwipeBackActivity implements
		OnClickListener {
	private static final String TAG = "PerfectDataActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_perfect_data);
		initView();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imageButton_left:
			back();
			break;
		case R.id.imageButton_right:
		case R.id.button_right:
			break;
		}
	}

	/**
	 * 组件初始化
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.echuang.tianyuanhao.swipebackbase.SwipeBackActivity#initView()
	 */
	@Override
	protected void initView() {
		initTitle("身份验证", "", 0);
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
}
