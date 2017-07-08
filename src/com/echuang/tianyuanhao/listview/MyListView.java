package com.echuang.tianyuanhao.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 我的list刷新
 * 
 * @ClassName: MyListView
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午11:29:17
 */
public class MyListView extends ListView implements Pullable {
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}
}
