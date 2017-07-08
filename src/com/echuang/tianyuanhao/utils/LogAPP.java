package com.echuang.tianyuanhao.utils;

import com.echuang.tianyuanhao.application.AppAppliction;

import android.util.Log;

/**
 * Log类
 * 
 * @ClassName: LogAPP
 * @Description: 代码中统一了log格式，之后添加log格式如下： Logz.i("tag", "info"); 带类型的如下：
 *               Logz.doLog("tag", “info, type); type可以是： Logz.V Logz.D Logz.I
 *               Logz.W Logz.E
 * @author 蒲江
 * @date 2016-7-11 上午10:16:07
 */
public class LogAPP {
	// 调试状态
	public static final int V = 11;
	public static final int D = 12;
	public static final int I = 13;
	public static final int W = 14;
	public static final int E = 15;

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (AppAppliction.isDebug) {
			Log.i(tag, "" + msg);
		}
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, int msg) {
		if (AppAppliction.isDebug) {
			Log.i(tag, "" + msg);
		}
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, long msg) {
		if (AppAppliction.isDebug) {
			Log.i(tag, "" + msg);
		}
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, boolean msg) {
		if (AppAppliction.isDebug) {
			Log.i(tag, "" + msg);
		}
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 *            Log.v() Log.d() Log.i() Log.w() and Log.e()
	 * @param type
	 *            0:v;1:d;2:i;3:w;4:e
	 */
	public static void doLog(String tag, String msg, int type) {
		if (AppAppliction.isDebug) {
			switch (type) {
			case V:
				Log.v(tag, msg + "");
				break;
			case D:
				Log.d(tag, msg + "");
				break;
			case I:
				Log.i(tag, msg + "");
				break;
			case W:
				Log.w(tag, msg + "");
				break;
			case E:
				Log.e(tag, msg + "");
				break;
			}
		}
	}
}
