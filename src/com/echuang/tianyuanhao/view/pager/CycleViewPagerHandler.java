package com.echuang.tianyuanhao.view.pager;

import android.content.Context;
import android.os.Handler;

/**
 * 防止内存泄漏，定义外部类，防止内部类对外部类的引用
 * 
 * @ClassName: CycleViewPagerHandler
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-22 下午3:02:26
 */
public class CycleViewPagerHandler extends Handler {
	Context context;

	public CycleViewPagerHandler(Context context) {
		this.context = context;
	}
};