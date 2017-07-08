package com.echuang.tianyuanhao.view.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.echuang.tianyuanhao.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * mageView创建工厂
 * 
 * @ClassName: ViewFactory
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-22 下午3:35:42
 */
public class ViewFactory {
	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @Title: getImageView
	 * @Description: TODO
	 * @param @param context
	 * @param @param url
	 * @param @return
	 * @return ImageView
	 * @throws
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
