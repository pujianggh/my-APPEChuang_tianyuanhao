package com.echuang.tianyuanhao.swipebackbase;

import android.os.Bundle;
import android.view.View;

import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.swipebackutils.SwipeBackLayout;
import com.echuang.tianyuanhao.swipebackutils.SwipeBackUtils;


/**
 * 基类
 * 
 * @ClassName: SwipeBackActivity
 * @Description: TODO 支持向左滑动返回
 * @author 蒲江
 * @date 2016-7-11 上午11:07:20
 */
public class SwipeBackActivity extends BaseFragmentActivity implements
		SwipeBackActivityBase {
	private SwipeBackActivityHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		SwipeBackUtils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void next() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub

	}
}
