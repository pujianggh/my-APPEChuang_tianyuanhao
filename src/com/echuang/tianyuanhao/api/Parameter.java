package com.echuang.tianyuanhao.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 参数类
 * 
 * @ClassName: Parameter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午9:49:50
 */
public class Parameter {

	private InitCallback callback;

	public static Application APP;

	public static Activity ACTIVITY;

	public static DisplayMetrics DM;

	/**
	 * 应用程序时候启动了主页
	 */
	public static boolean ISRunning = false;

	public Parameter() {
	}

	/**
	 * 系统核心库初始化
	 * 
	 * @Title: initSystem
	 * @Description: TODO 将应用Application传递到核心库中做初始化动作
	 * @param @param app
	 * @param @return
	 * @return Parameter
	 * @throws
	 */
	public Parameter initSystem(Application app) {
		APP = app;
		// 系统配置
		PropertiesManager.getInstance(app);
		// 日志开关
		// initLog();
		// api地址初始化
		// AddressManager.getInstance(app);

		// 屏幕密度参数
		DM = app.getResources().getDisplayMetrics();
		// 其它初始化回调
		if (this.callback != null) {
			this.callback.onInit(APP);
		}
		return this;
	}

	/**
	 * 设置初始化
	 * 
	 * @Title: setCallBack
	 * @Description: TODO 初始化回掉监听器
	 * @param @param initCallback
	 * @param @return
	 * @return Parameter
	 * @throws
	 */
	public Parameter setCallBack(InitCallback initCallback) {
		this.callback = initCallback;
		return this;
	}

	/**
	 * 初始化
	 * 
	 * @ClassName: InitCallback
	 * @Description: TODO 初始化对外回掉
	 * @author 蒲江
	 * @date 2016-7-11 上午9:50:10
	 */
	public interface InitCallback {
		void onInit(Context context);
	}
}
