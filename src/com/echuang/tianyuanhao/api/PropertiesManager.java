package com.echuang.tianyuanhao.api;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.echuang.tianyuanhao.utils.Utils;

/**
 * 系统配置参数管理类
 * @ClassName: PropertiesManager
 * @Description: 直接调用get方法，获取config/system.properties中对应的value
 * @author 蒲江
 * @date 2016-7-11 上午9:46:18
 */
public class PropertiesManager {

	private static Properties properties;

	private static PropertiesManager instance = null;

	private PropertiesManager(Context context) {
		InputStream is = null;
		try {
			is = Parameter.APP.getAssets().open("config/system.properties");
			properties = new Properties();
			properties.load(is);
		} catch (IOException e) {
			Log.e("", e.getMessage());
		} finally {
			Utils.closeStream(is);
		}
	}

	/**
	 * 实例化
	 * @Title: getInstance
	 * @Description: 该方法在系统启动的时候调用
	 * @param @param context
	 * @param @return
	 * @return PropertiesManager
	 * @throws
	 */
	public static PropertiesManager getInstance(Context context) {
		if (instance == null) {
			instance = new PropertiesManager(context);
		}
		return instance;
	}

	public static PropertiesManager getInstance() {
		return instance;
	}

	/**
	 * 取值
	 * @Title: get
	 * @Description: 输入的参数为config/system.properties中的key
	 * @param @param key
	 * @param @param defaultValue
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * 取值
	 * @Title: get
	 * @Description: 输入的参数为config/system.properties中的key
	 * @param @param key
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String get(String key) {
		return properties.getProperty(key, "");
	}
}
