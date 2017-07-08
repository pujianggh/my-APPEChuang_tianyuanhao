package com.echuang.tianyuanhao.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.api.Parameter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.netutils.RequestParams;
import com.echuang.tianyuanhao.view.CustomDialog;

/**
 * 工具类集合
 * 
 * @ClassName: Utils
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午10:02:04
 */
@SuppressLint("NewApi")
public class Utils {

	public static final String NUMBERFORMATS = "#,###.0#";

	/*	*//**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	/*
	 * public static void doLog(String tag, String msg) { if
	 * (SettingStr.isDebug) { Log.i(tag, "" + msg); } }
	 *//**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	/*
	 * public static void doLog(String tag, int msg) { if (SettingStr.isDebug) {
	 * Log.i(tag, "" + msg); } }
	 *//**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	/*
	 * public static void doLog(String tag, long msg) { if (SettingStr.isDebug)
	 * { Log.i(tag, "" + msg); } }
	 *//**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	/*
	 * public static void doLog(String tag, boolean msg) { if
	 * (SettingStr.isDebug) { Log.i(tag, "" + msg); } }
	 *//**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 *            Log.v() Log.d() Log.i() Log.w() and Log.e()
	 * @param type
	 *            0:v;1:d;2:i;3:w;4:e
	 */
	/*
	 * public static void doLog(String tag, String msg, int type) { if
	 * (SettingStr.isDebug) { switch (type) { case 0: Log.v(tag, msg+""); break;
	 * case 1: Log.d(tag, msg+""); break; case 2: Log.i(tag, msg+""); break;
	 * case 3: Log.w(tag, msg+""); break; case 4: Log.e(tag, msg+""); break; } }
	 * }
	 */

	/**
	 * Close outputStream
	 * 
	 * @param stream
	 */
	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
				stream = null;
			} catch (IOException e) {
				// Log.e(e.getMessage());
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						stream = null;
						// Log.e(e.getMessage());
						e.printStackTrace();
					}
					stream = null;
				}
			}
		}
	}

	/**
	 * 网络是否已经连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		return networkinfo != null && networkinfo.isAvailable();
	}

	/**
	 * 编码URL
	 * 
	 * @param url
	 * @return
	 */
	public static String encoderUrl(String url) {
		return URLEncoder.encode(url);
	}

	/**
	 * Base64编码<br>
	 * 将字符串编码成base64编码;编码标准为RFC 2045
	 * 
	 * @param str
	 * @return
	 */
	public static String base64_encode(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		String result = "";
		try {
			result = Base64.encodeToString(str.getBytes("UTF-8"),
					Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Base64解码<br>
	 * 解码被编码的字符串
	 * 
	 * @param base
	 * @return
	 */
	public static String base64_decode(String base) {
		if (TextUtils.isEmpty(base)) {
			return "";
		}
		String result = "";
		try {
			result = new String(Base64.decode(base, Base64.DEFAULT), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isLocationOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}

		return false;
	}

	/**
	 * 判断GPS是否开启
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isGPSOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 强制帮用户打开GPS
	 * 
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param message
	 * @return
	 */

	public static boolean isEmpty(String message) {
		boolean isNull = false;
		try {
			if (message.equals("") || message == null) {
				isNull = false;
			} else {
				isNull = true;
			}

		} catch (NullPointerException e) {

			isNull = false;
		}
		return isNull;
	}

	/**
	 * px(像素)转成dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * dip转成px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px(像素)转成dip
	 */
	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static int getScreen(Context context, int type) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		if (type == 0) {
			return dm.widthPixels;
		}
		return dm.heightPixels;
	}

	// 从assets 文件夹中获取文件并读取数�?
	public static String getFromAssets(String fileName, Context context) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组�?
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回不同分辨率图�?
	 * 
	 * @param url
	 * @param type
	 *            0:150x150 1:360x180 2:360x640
	 * @return
	 */
	public static String getImageUrl(String url, int type) {
		if (url.lastIndexOf(".") <= 0) {
			return url;
		}
		String temp = url.substring(url.lastIndexOf("."));
		switch (type) {
		case 0:
			temp = "_150x150" + temp;
			break;
		case 1:
			temp = "_360x180" + temp;
			break;
		case 2:
			temp = "_360x640" + temp;
			break;
		case 3:
			temp = "_240x240" + temp;
			break;
		case 4:
			temp = "";
			break;
		}
		return url + temp;
		// return url;// 返回原url

	}

	/****
	 * long类型的时间转化为字符
	 * 
	 * @param time
	 * @return
	 */
	public static String longToTime(String time, String type) {
		String msg = "";
		if (Utils.isEmpty(time)) {
			try {
				long l = Long.parseLong(time);
				msg = DateFormat.format(type, l).toString();
			} catch (NullPointerException e) {
				msg = "";
			}
		}
		return msg;
	}

	// 提示对话
	public static void ShowTipsDialog(String message, Context context) {
		CustomDialog.Builder dialog = new CustomDialog.Builder(context);
		dialog.setTitle("温馨提示");
		dialog.setMessage(message);
		dialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		dialog.create().show();
	}

	/**
	 * 返回当前程序版本
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 返回当前程序code
	 */
	public static int getAppVersionCode(Context context) {
		int versionName = 1;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionCode;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 显示时间
	 * 
	 * @param timestr
	 * @return
	 */
	public static String getShowTime(String timestr) {
		String str = "";

		try {
			if (timestr != null && !"".equals(timestr)) {
				Calendar oldCal = Calendar.getInstance();
				Calendar newCal = Calendar.getInstance();
				oldCal.setTime(new Date(System.currentTimeMillis()));
				newCal.setTime(new Date(Long.parseLong(timestr)));
				long time = Long.parseLong(timestr);
				if (Math.abs(newCal.get(Calendar.DAY_OF_YEAR)
						- oldCal.get(Calendar.DAY_OF_YEAR)) == 1) {
					str = DateFormat.format(" kk:mm", time).toString();
					str = "昨天";
				} else if (Math.abs(newCal.get(Calendar.DAY_OF_YEAR)
						- oldCal.get(Calendar.DAY_OF_YEAR)) <= 1) {
					str = DateFormat.format("kk:mm", time).toString();
					str = "今天";
				} else {
					str = DateFormat.format("MM-dd", time).toString();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFormatTime(String timestr, int type) {
		String str = "";
		try {
			if (timestr != null && !timestr.equals("")) {
				long time = Long.parseLong(timestr);
				str = DateFormat.format("yyyy-MM-dd kk:mm", time).toString();
				if (type == 1) {
					str = DateFormat.format("yyyy-MM-dd", time).toString();

				}
				if (type == 2) {
					str = DateFormat.format("yyyy-MM-dd kk:mm:ss", time)
							.toString();
				}
				if (type == 3) {
					str = DateFormat.format("MM月dd日 kk:mm", time).toString();
				}
				if (type == 4) {
					str = DateFormat.format("kk:mm", time).toString();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return str;
	}

	@SuppressLint("SimpleDateFormat")
	public static String stringTolongstr(String timestr) {
		String time = "";
		try {
			if (timestr != null && !timestr.equals("")) {
				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd kk:mm:ss");
				time = "" + sDateFormat.parse(timestr).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return time;
	}

	// 获取当前日期
	@SuppressLint("SimpleDateFormat")
	public static String GetCurrentDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	// 防止重复点击
	private static long lastClickTime;

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 400 && time - lastClickTime > -1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 获取banner高度
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public static int getBannerHeight(Context context, int type) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int width = dm.widthPixels;// 宽度
		int height = dm.heightPixels;// 宽度
		int temp = width;
		switch (type) {
		case 0:
			temp = height / 4 * 1;
			break;
		case 1:
			temp = height / 3 * 1;
			break;
		case 2:
			temp = height / 9 * 1;
			break;
		}
		return temp;
	}

	/**
	 * 获取banner json
	 * 
	 * @param context
	 * @return
	 */
	public static String getBannerJson(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SharedStr.SP_FILE,
				Context.MODE_PRIVATE);
		return sp.getString("bannerjson", "");
	}

	/**
	 * 设置banner json
	 * 
	 * @param context
	 * @return
	 */
	public static void setBannerJson(Context context, String str) {
		SharedPreferences sp = context.getSharedPreferences(SharedStr.SP_FILE,
				Context.MODE_PRIVATE);
		sp.edit().putString("bannerjson", str).commit();
	}

	public static void doErrorMessage(FragmentActivity activity, String content) {

	}

	public static String getCountDownForRaise(String timestr) {
		String str = "";
		str = "0天0时0分0秒";
		try {
			if (timestr != null) {
				long time = Long.parseLong(timestr);
				long diff = time;
				long days = diff / (24 * 60 * 60 * 1000);
				long hour = (diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
				long minute = (diff % (60 * 60 * 1000)) / (60 * 1000);
				long second = (diff % (60 * 1000)) / 1000;
				if (days < 0) {
					days = 0;
				}
				;
				if (hour < 0) {
					hour = 0;
				}
				;
				if (minute < 0) {
					minute = 0;
				}
				;
				if (second < 0) {
					second = 0;
				}
				;
				str = days + "天" + hour + "时" + minute + "分" + second + "秒";
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}

		return str;
	}

	public static String getCountDown(String timestr, int type) {
		String str = "";
		str = "0天0时0分0秒";
		try {
			if (timestr != null) {
				long time = Long.parseLong(timestr);
				long current = System.currentTimeMillis();
				long diff = time - current;
				long days = diff / (24 * 60 * 60 * 1000);
				long hour = (diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
				long minute = (diff % (60 * 60 * 1000)) / (60 * 1000);
				long second = (diff % (60 * 1000)) / 1000;
				if (days < 0) {
					days = 0;
				}
				;
				if (hour < 0) {
					hour = 0;
				}
				;
				if (minute < 0) {
					minute = 0;
				}
				;
				if (second < 0) {
					second = 0;
				}
				;
				str = days + "天" + hour + "时" + minute + "分" + second + "秒";
				if (type == 1) {
					str = (days * 24 + hour) + ":" + minute + ":" + second;
				}
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}

		return str;
	}

	public static String addParams(Map<String, String> map) {
		RequestParams rp = new RequestParams();
		rp.setChannel(AppAppliction.CHANNEL);
		rp.setVersion(AppAppliction.VERSION);
		if (null == map) {
			map = new HashMap<String, String>();
		}
		rp.setParams(map);
		rp.setSign(AppUtil.addSign(AppUtil.genSignData(rp.getParams()),
				AppAppliction.SIGN));
		return JSONObject.toJSONString(rp);
	}

	public static void setOrangText(TextView textView) {

	}

	public static void setFontScale(Context context) {
		Configuration config = context.getResources().getConfiguration();
		if (config.fontScale > 1.0) {
			config.fontScale = (float) 1.0;
			context.getResources().updateConfiguration(config,
					context.getResources().getDisplayMetrics());
		}
	}

	// 计算string字符串总长度，可以结合屏幕宽度算行数
	public static float calcuLine(Context context, String str, int textsize,
			int padding) {
		TextPaint fontPaint = new TextPaint();
		fontPaint.setTextSize(textsize);
		float length = fontPaint.measureText(str);
		float screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth()
				- padding;
		return length / screenWidth;
	}

	/************** 硬件加速相关 S **************/
	public static boolean isHardwareAccelerated(View view) {
		boolean accelerated = false;
		try {
			Class viewClass = View.class;
			Method isHardwareAccelerated = viewClass.getMethod(
					"isHardwareAccelerated", null);
			accelerated = (Boolean) isHardwareAccelerated.invoke(view, null);
		} catch (Exception e) {

		}

		return accelerated;
	}

	public static int getFlagHardWareAccelerated() {// 通过反射获得WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
		try {
			Field field = WindowManager.LayoutParams.class
					.getField("FLAG_HARDWARE_ACCELERATED");
			int result = field.getInt(WindowManager.LayoutParams.class); // 0x01000000
			return result;
		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		} catch (SecurityException e) {

		} catch (NoSuchFieldException e) {

		}
		return -1;
	}

	// 开启Window级别硬件加速
	public static void setWindowHardWareAccelerated(Activity activity) {
		int flagHardWareAccelerated = getFlagHardWareAccelerated();
		if (flagHardWareAccelerated != -1) {
			activity.getWindow().setFlags(flagHardWareAccelerated,
					flagHardWareAccelerated);
		}
	}

	/************** 硬件加速相关 E **************/

	// 出场动画
	public static LayoutAnimationController getAnimationController() {
		int duration = 300;
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

	/**
	 * @param price
	 *            float
	 * @param format
	 *            String
	 * @return
	 * @author zhaotian
	 * @date 2015年8月1日 下午1:43:05
	 * @Description: TODO(货币格式化)
	 */

	public static String NumberFormatTool(float price, String format) {
		if (price == 0 || price == 0.00) {
			return "0.00";
		}
		NumberFormat numberFormat = new DecimalFormat(format);
		String str = numberFormat.format(price);
		if (str.isEmpty()) {
			return "0.00";
		} else {
			return str;
		}
	}

	public static String LongFormatString(long price, String format) {
		if (price == 0 || price == 0.00) {
			return "0.00";
		}

		NumberFormat numberFormat = new DecimalFormat(format);
		String str = numberFormat.format(price);
		if (str.isEmpty()) {
			return "0.00";
		} else {
			return str;
		}
	}

	/**
	 * @param str
	 * @return float
	 * @author zhaotian
	 * @date 2015年8月7日 下午5:19:31
	 * @Description:
	 */
	public static float StringToFloat(String str) {
		String newStr = "";
		try {
			if (str.contains(",")) {
				newStr = str.replace(",", "");
			} else {
				newStr = str;
			}

			BigDecimal b = new BigDecimal(newStr);
			float f1 = b.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
			return f1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}

	public static double StringTwoDouble(String str) {
		String newStr = "";
		try {
			if (str.isEmpty()) {
				return 0.00;
			} else {
				if (str.contains(",")) {
					newStr = str.replace(",", "");
				} else {
					newStr = str;
				}

				double d1 = Double.valueOf(newStr);
				return d1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0.00;
		}
	}

	public static long StringToLong(String str) {
		long l = 0;
		try {
			if (str.isEmpty()) {
				return 0;
			} else {
				if (str.contains(",")) {
					str = str.replace(",", "");
					if (str.contains(".")) {
						str = str.replace(".", "");
					}
				}
				l = Long.parseLong(str);
				l = l * 100;
				return l;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static float StringToDoubleToFloat(String str) {
		NumberFormat numberFormat = new DecimalFormat(str);
		try {
			Number number = numberFormat.parse(str);
			String ss = String.valueOf(number.doubleValue());
			return Float.valueOf(ss);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0f;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static double StringToDouble(String str) {
		NumberFormat numberFormat = new DecimalFormat(str);
		try {
			Number number = numberFormat.parse(str);
			return number.doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.00;
		}
	}

	public static double DoubleToDouble(double dd) {
		try {
			BigDecimal b = new BigDecimal(dd);
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.00;
		}
	}

	public static String PercentFormat(double percent) {
		if (percent == 0 || percent == 0.00) {
			return "0%";
		}
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(2);// 设置保留小数位
		percentFormat.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		return percentFormat.format(percent);
	}

	public static String getDoublePercent(double percent) {
		String percentStr = "";
		try {
			if (percent == 0 && percent == 0.00) {
				return "0.00%";
			} else {
				percentStr = String.valueOf(percent) + "%";
				return percentStr;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "0.00%";
		}
	}

	public static String getPercent(double x, int total) {
		String result = "";// 接受百分比的值
		double tempresult = x / total;
		DecimalFormat df1 = new DecimalFormat("0.00%"); // ##.00%
														// 百分比格式，后面不足2位的用0补齐
		result = df1.format(tempresult);
		return result;
	}

	// 复制文字到剪贴板
	public static void copy(String content, Context context) {
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
		Toast.makeText(Parameter.APP, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 用户名隐藏信息
	 * 
	 * @Title: userNameToHide
	 * @Description: TODO
	 * @param @param content
	 * @return void
	 * @throws
	 */
	public static String userNameToHide(String content) {
		String newUserName = "";
		if (content != null) {
			System.out.println(content);
			if (content.length() == 11) {
				newUserName = content.substring(0,
						content.length() - (content.substring(3)).length())
						+ "****" + content.substring(7);
			} else if (content.length() >= 3) {
				newUserName = content.substring(0,
						content.length() - (content.substring(2)).length())
						+ "****" + content.substring(content.length() - 2);
			} else {
				newUserName = "******";
			}
		}
		return newUserName;
	}

	/**
	 * 
	 * @Title: logOut
	 * @Description: 退出方法
	 * @param @param context 设定文件
	 * @return void 返回类型
	 */
	public static void logOut(Context context) {
		/* 极光部分 */
		// HashSet<String> hs = new HashSet<String>();
		// hs.add("null");
		// JPushInterface.setTags(context, hs, null);
		// JPushInterface.resumePush(context);
		// 保存pushKey
		SharedPreUtils.getInstance(context).saveJpushKey("");
		// 保存账户
		SharedPreUtils.getInstance(context).saveUserRealName("");
		SharedPreUtils.getInstance(context).saveUserInvitationCode("");
		// 保存首页图标
		SharedPreUtils.getInstance(context).saveUserHomeImageUrl("");
		// 保存用户是否为企业
		SharedPreUtils.getInstance(context).saveUserIsCompany(false);
		SharedPreUtils.getInstance(context).saveUserGroupCompanyId("");
		// 保存用户id
		SharedPreUtils.getInstance(context).saveUserId("");
		// 保存密码
		SharedPreUtils.getInstance(context).savePassWord("");
		// 保存首页图标
		SharedPreUtils.getInstance(context).saveUserHomeImageUrl("");
		// 保存登录
		SharedPreUtils.getInstance(context).saveIsLogin(false);
		// 下次进来是否需要设置手势密码
		SharedPreUtils.getInstance(context).saveSetGestureNext(false);
		// 默认打开设置里手势密码
		SharedPreUtils.getInstance(context).saveGesture(false);
		// 手機認證
		SharedPreUtils.getInstance(context).saveUserTel("");
		// LocalBroadcastManager.getInstance(context).sendBroadcast(
		// new Intent(SettingMainActivity.RECEIVER_LOGOUT));

		// String url = UrlManage.BASE_URL + UrlManage.USER_LOGOUT;

		// com.loopj.android.http.RequestParams params = new
		// com.loopj.android.http.RequestParams();

	}

	// 判断字符串是否为数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static void getTextViewOperateAmountStr(Context context,
			TextView textView, String str) {
		if (!str.isEmpty()) {
			if (str.startsWith("-")) {
				textView.setText(str);
				textView.setTextColor(context.getResources().getColor(
						R.color.text_blue));
			} else {
				str = "+" + str;
				textView.setText(str);
				textView.setTextColor(context.getResources().getColor(
						R.color.text_brown));
			}
		}
	}

	/**
	 * 获取手机ip地址
	 * 
	 * @return
	 */
	public static String getPhoneIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			return "10.10.10.1";
		}
		return "10.10.10.10";
	}

	/**
	 * 获取IP
	 * 
	 * @Title: getPhoneIpForSwing
	 * @Description: TODO
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String GetHostIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
		}
		return "10.10.10.1";
	}

	// 去除首字0
	public static String FormatFirstZero(String num) {
		if (num.isEmpty()) {
			return "";
		} else {
			return num.replaceAll("^(0+)", "");
		}
	}

	// 去除首字0
	public static String getPrettyNumber(String number) {
		if (number.isEmpty()) {
			return "";
		} else {
			return BigDecimal.valueOf(Double.parseDouble(number))
					.stripTrailingZeros().toPlainString();
		}
	}

	//
	public static String FormatTel(String tel) {
		if (tel.isEmpty()) {
			return "";
		} else {
			String nTel = "";
			nTel = tel.substring(0, 3) + "****" + tel.substring(7);
			return nTel;
		}
	}

	public static void saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(),
				"Ztrong");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.parse("file://" + "Ztrong")));
	}

	/**
	 * 获取商品详情图片
	 * 
	 * @param id
	 * @return url
	 */
	public static String GoodsDetailUrl(String id) {
		return "http://static.ztrong.com/upload_images/commodity/" + id + "/"
				+ id + ".png";
	}

	/**
	 * 获取商品列表图片
	 * 
	 * @param id
	 * @return
	 */
	public static String GoodsListUrl(String id) {
		return "http://static.ztrong.com/upload_images/commodity/" + id
				+ "/list/" + id + ".png";
	}

	public static String GoodsDateilUrl(String idGoods) {
		return "http://static.ztrong.com/upload_images/commodity/" + idGoods
				+ "/" + idGoods + ".png";
	}

	// 获取商品图片拼接地址
	public static String getGoodsPicUrl(String str, int type) {
		// 详情
		// http://static.ztrong.com/upload_images/commodity/商品ID/商品ID.png
		// 列表
		// http://static.ztrong.com/upload_images/commodity/商品ID/list/商品ID.png
		// 灰图
		// http://static.ztrong.com/upload_images/commodity/商品ID/gray/商品ID.jpg
		// 亮图 http://10.18.11.136
		// http://static.ztrong.com/upload_images/commodity/商品ID/light/商品ID.jpg
		if ("".equals(str)) {
			return "";
		}
		switch (type) {
		case 0:
			str = "http://static.ztrong.com/upload_images/commodity/"
					+ str.trim() + "/" + str.trim() + ".png";
			break;
		case 1:
			str = "http://static.ztrong.com/upload_images/commodity/"
					+ str.trim() + "/list/" + str.trim() + ".png";
			break;
		case 2:
			str = "http://static.ztrong.com/upload_images/commodity/"
					+ str.trim() + "/gray/" + str.trim() + ".jpg";
			break;
		case 3:
			str = "http://static.ztrong.com/upload_images/commodity/"
					+ str.trim() + "/light/" + str.trim() + ".jpg";
			break;
		}
		return str;
	}

	/**
	 * 获取圆角位图的方法
	 * 
	 * @Title: toRoundCorner
	 * @Description: TODO
	 * @param @param bitmap 需要转化成圆角的位图
	 * @param @param pixels 圆角的度数，数值越大，圆角越大
	 * @author pujiang
	 * @param @return 处理后的圆角位图
	 * @return Bitmap
	 * @throws
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获取application中指定的meta-data
	 * 
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return resultData;
	}

	public static String getChannel(Context context) {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		String sourceDir = applicationInfo.sourceDir;
		String ret = "";
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(sourceDir);
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String entryName = entry.getName();
				if (entryName.startsWith("META-INF/ztrongchannel")) {
					ret = entryName;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != zipFile) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String[] split = ret.split("_");
		if (split != null && split.length >= 2) {
			return ret.substring(split[0].length() + 1);
		} else {
			return "";
		}
	}

	public static boolean checkPermission(Context context, String permission) {
		boolean result = false;
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				Class<?> clazz = Class.forName("android.content.Context");
				Method method = clazz.getMethod("checkSelfPermission",
						String.class);
				int rest = (Integer) method.invoke(context, permission);
				if (rest == PackageManager.PERMISSION_GRANTED) {
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				result = false;
			}
		} else {
			PackageManager pm = context.getPackageManager();
			if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
				result = true;
			}
		}
		return result;
	}

	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = null;
			if (checkPermission(context,
					android.Manifest.permission.READ_PHONE_STATE)) {
			}
			device_id = tm.getDeviceId();
			String mac = null;
			FileReader fstream = null;
			try {
				fstream = new FileReader("/sys/class/net/wlan0/address");
			} catch (FileNotFoundException e) {
				fstream = new FileReader("/sys/class/net/eth0/address");
			}
			BufferedReader in = null;
			if (fstream != null) {
				try {
					in = new BufferedReader(fstream, 1024);
					mac = in.readLine();
				} catch (IOException e) {
				} finally {
					if (fstream != null) {
						try {
							fstream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			json.put("mac", mac);
			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}
			json.put("device_id", device_id);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
