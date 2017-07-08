package com.echuang.tianyuanhao.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 一般信息提示类
 * 
 * @ClassName: CustomToast
 * @Description: 保证同时只有一个toast出现
 * @author 蒲江
 * @date 2016-7-8 下午5:37:00
 */
public class CustomToast {
	private static Toast mToast;

	public static void showToast(Context mContext, String text, int duration) {

		if (mToast != null)
			mToast.setText(text);
		else
			mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);

		mToast.show();
	}

	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}
}
