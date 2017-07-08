package com.echuang.tianyuanhao.utils;

import com.echuang.tianyuanhao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 工具类
 * 
 * @ClassName: ImageLoaderHelper
 * @Description: TODO ImageLoader辅助工具
 * @author 蒲江
 * @date 2016-7-11 上午10:24:20
 */
public class ImageLoaderHelper {

	private static DisplayImageOptions.Builder getDisplayImageOptionsBuilder() {
		return new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).showImageForEmptyUri(R.drawable.default_img)
				.showImageOnFail(R.drawable.default_img)
				.bitmapConfig(Bitmap.Config.ARGB_8888);
	}

	public static DisplayImageOptions getDefaultDisplayImageOptions() {
		return getDisplayImageOptionsBuilder().build();
	}

	public static void displayImageWithDefault(String uri, ImageView imageView,
			int defaultResId) {
		displayImage(
				uri,
				imageView,
				getDisplayImageOptionsBuilder()
						.showImageForEmptyUri(defaultResId)
						.showImageOnFail(defaultResId).build());
	}

	public static void displayRoundImage(String uri, ImageView imageView,
			int radius) {
		displayImage(
				uri,
				imageView,
				getDisplayImageOptionsBuilder().displayer(
						new RoundedBitmapDisplayer(radius)).build());
	}

	public static void displayRoundImage(String uri, ImageView imageView,
			int radius, SimpleImageLoadingListener listener) {
		displayImage(
				uri,
				imageView,
				getDisplayImageOptionsBuilder().displayer(
						new RoundedBitmapDisplayer(radius)).build(), listener);
	}

	public static void displayImage(String uri, ImageView imageView) {
		ImageLoader.getInstance().displayImage(uri, imageView);
	}

	public static void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options) {
		ImageLoader.getInstance().displayImage(uri, imageView, options);
	}

	public static void displayImage(String uri, ImageView imageView,
			SimpleImageLoadingListener listener) {
		ImageLoader.getInstance().displayImage(uri, imageView, listener);
	}

	public static void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options, SimpleImageLoadingListener listener) {
		ImageLoader.getInstance().displayImage(uri, imageView, options,
				listener);
	}
}
