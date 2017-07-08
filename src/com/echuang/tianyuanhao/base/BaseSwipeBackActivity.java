package com.echuang.tianyuanhao.base;

import com.echuang.tianyuanhao.swipebackbase.SwipeBackActivity;

/**
 * 抽象类BaseSwipeBackActivity
 * 作用：方便继承，减少类与类之间的耦合
 * @ClassName: BaseSwipeBackActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午1:45:08
 */
public abstract class BaseSwipeBackActivity extends SwipeBackActivity{

	@Override
	public void setSwipeBackEnable(boolean enable) {
		// TODO Auto-generated method stub
		super.setSwipeBackEnable(enable);
	}

}
