package com.echuang.tianyuanhao.utils;

import android.graphics.Bitmap;

import com.echuang.tianyuanhao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 
 * @ClassName: ImageLoadOptions
 * @Description: TODO
 * @author 蒲江
 * @date 2015-8-20 上午11:24:22
 */
public class ImageLoadOptions {

	public static DisplayImageOptions getOptions() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.default_img)
				.showImageOnFail(R.drawable.default_img)
				.cacheInMemory(true)
				// .cacheOnDisk(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();//
		return defaultOptions;
	}
}
