package com.echuang.tianyuanhao.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.MainActivity;
import com.echuang.tianyuanhao.api.Parameter;
import com.echuang.tianyuanhao.imageconfig.CacheConfig;
import com.echuang.tianyuanhao.imageconfig.ImageLoader;
import com.echuang.tianyuanhao.utils.CustomCrashHandler;
import com.echuang.tianyuanhao.utils.FileUtils;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;
import com.echuang.tianyuanhao.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 项目类实例
 * 
 * @ClassName: ZtrongAppliction
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午10:12:42
 */
public class AppAppliction extends Application {
	private static AppAppliction instance;
	public static final boolean isDebug = true;// 是否打印本APP日志，设置true:打印 false:不打印
	public static final String VERSION = "3.1";// 版本号：主要作用版本控制
	public static final String CHANNEL = "Android";// 渠道号：主要作用区分来源，便于统计
	public static final String SIGN = "51e4dc1269013ccd8f257164a79f465a";// APP数字签名
	private List<Activity> activityList = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.defaultDisplayImageOptions(
						ImageLoaderHelper.getDefaultDisplayImageOptions())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(
				config);
		settingImageLoader();
		new Parameter().setCallBack(new Parameter.InitCallback() {
			@Override
			public void onInit(Context context) {
				// TODO 初始化操作
			}
		}).initSystem(this);
		CustomCrashHandler mCustomCrashHandler = CustomCrashHandler
				.getInstance();
		mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
		Utils.setFontScale(this);
	}

	private JSONObject getConfig() throws IOException, JSONException {
		JSONObject qdObj = null;
		AssetManager assetManager = getAssets();
		InputStream is = assetManager.open("cfg.json");
		byte[] buffer = new byte[is.available()];
		while (is.read(buffer) != -1)
			;
		String json = new String(buffer);
		qdObj = new JSONObject(json);
		return qdObj;
	}

	public static synchronized AppAppliction getInstance() {
		if (null == instance) {
			instance = new AppAppliction();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void clear() {
		try {
			for (Activity activity : activityList) {
				activity.finish();
			}
			activityList.clear();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			startActivity(new Intent(this, MainActivity.class));
		}
	}

	public void exit() {
		try {
			for (Activity activity : activityList) {
				activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	private void settingImageLoader() {
		ImageLoader.init(getApplicationContext(), new CacheConfig()
				.setDefRequiredSize(1000)
				// 设置默认的加载图片尺寸（表示宽高任一不超过该值，默认是70px）
				.setDefaultResId(R.drawable.default_img)
				// 设置显示的默认图片（默认是0，即空白图片）
				.setBitmapConfig(Bitmap.Config.ARGB_8888)
				// 设置图片位图模式（默认是Bitmap.CacheConfig.ARGB_8888）
				.setMemoryCachelimit(Runtime.getRuntime().maxMemory() / 1) // 设置图片内存缓存大小（默认是Runtime.getRuntime().maxMemory()
				// / 4）
				.setFileCachePath(FileUtils.DATAPATH+"/") // 设置文件缓存保存目录
														// Medisharecache
				);
	}
}
