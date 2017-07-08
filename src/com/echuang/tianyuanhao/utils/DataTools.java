package com.echuang.tianyuanhao.utils;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * 工具类
 * 
 * @ClassName: DataTools
 * @Description: TODO
 * @author 蒲江
 * @date 2015-7-28 下午2:28:58
 */
public class DataTools {

	/**
	 * 根据自身的屏幕获取对应的比例的图片
	 * 
	 * @Title: getPicPathForPhone
	 * @Description: TODO
	 * @param @param context
	 * @param @param picName
	 * @param @param picPrefix
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getPicPathForPhone(Context context, String picName,
			String picPrefix) {
		String defaultPicFile = "iphone5";
		return picPrefix.trim() + defaultPicFile + "/" + picName.trim();
	}

	/**
	 * 设置ListView高度
	 * 
	 * @author pujiang 2014-6-14
	 * @param listView
	 *            TODO 要设置高度的ListView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * TODO ：dip转为 px
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月19日 下午2:09:19
	 * @version 1.0
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * TODO ：px 转为 dip
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月19日 下午2:09:30
	 * @version 1.0
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * TODO ：获取屏幕的宽度
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月19日 下午2:08:55
	 * @version 1.0
	 * @param activity
	 * @return
	 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取版本号
	 * 
	 * @author pujiang 2014-6-14
	 * @param context
	 * @return TODO 版本号
	 */
	public static String getVersionCode(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return String.valueOf(info.versionCode);
	}

	/**
	 * 获取版本名称
	 * 
	 * @fileName DataTools.java
	 * @description TODO
	 * @created 2014 2014年8月28日 下午3:14:50
	 * @author jiang_pu
	 */
	public static String getVersionName(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	/**
	 * 
	 * @Title: getWindowWidth
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getWindowWidth(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context
				.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		return mScreenWidth;
	}

	/**
	 * 
	 * @Title: getWindowHeigh
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getWindowHeigh(Context context) {
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context
				.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenHeigh = dm.heightPixels;
		return mScreenHeigh;
	}

	/**
	 * 更具图片比例计算图片放大或缩小的比例
	 * @Title: compressionPhoto
	 * @Description: TODO
	 * @param @param screenWidth
	 * @param @param filePath
	 * @param @param ratio
	 * @param @return
	 * @author pujiang
	 * @return Bitmap
	 * @throws
	 */
	public static Bitmap compressionPhoto(Context mContext, Bitmap bitmap,int mWindowWidth) {
		Bitmap compressionBitmap = null;
		//float Ph = getWindowHeigh(mContext) - 200;
		float Pw = getWindowWidth(mContext) - mWindowWidth;
		float scale = Pw / bitmap.getWidth();
		// float scaleHeight = Ph / bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		try {
			compressionBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			return bitmap;
		}
		return compressionBitmap;
	}

	/**
	 * 根据图片比例计算图片相对高度和宽度，设置控件的高度和宽度
	 * @Title: compressionPhoto
	 * @Description: TODO
	 * @param @param mContext
	 * @param @param bitmap
	 * @param @return
	 * @author pujiang
	 * @return Bitmap
	 * @throws
	 */
	public static Bitmap compressionPhotoForWinning(Context mContext,
			Bitmap bitmap, RelativeLayout relativeLayout, int rowNum,
			int columnNum,float WindowWidth) {
		float scaleWidth = bitmap.getWidth();
		float scaleHeight = bitmap.getHeight();
		float mScale = scaleWidth/scaleHeight;
		int mImageW = (int)WindowWidth/columnNum;
		int mImageH = (int)(WindowWidth/columnNum/mScale);
		/**
		 * 设置布局文件的高和宽
		 */
		ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) relativeLayout
				.getLayoutParams();
		lp.height = mImageH;
		lp.width = mImageW;
//		Matrix matrix = new Matrix();
//		matrix.postScale(mImageW, mImageH);
//		try {
//			compressionBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//		} catch (Exception e) {
//			return bitmap;
//		}
		return bitmap;
	}
}
