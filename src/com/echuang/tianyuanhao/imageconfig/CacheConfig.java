package com.echuang.tianyuanhao.imageconfig;

import android.graphics.Bitmap;

/**
 * 参数类
 * 
 * @ClassName: CacheConfig
 * @Description: TODO 作用于图片参数
 * @author 蒲江
 * @date 2016-7-11 上午10:45:21
 */
public class CacheConfig {

	/**
	 * 默认的加载图片尺寸（表示宽高任一不超过该值，默认70
	 */
	private int defRequiredSize = 70;

	/**
	 * 显示的默认图片
	 */
	private int defaultResId;

	/**
	 * 图片位图模式（默认是Bitmap.CacheConfig.ARGB_8888
	 */
	private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

	/**
	 * 图片内存缓存大小（默认是Runtime.getRuntime().maxMemory()
	 */
	private long memoryCachelimit;

	/**
	 * 文件缓存保存目录
	 */
	private String fileCachePath;

	public int getDefRequiredSize() {
		return defRequiredSize;
	}

	public CacheConfig setDefRequiredSize(int defRequiredSize) {
		this.defRequiredSize = defRequiredSize;
		return this;
	}

	public int getDefaultResId() {
		return defaultResId;
	}

	public CacheConfig setDefaultResId(int defaultResId) {
		this.defaultResId = defaultResId;
		return this;
	}

	public Bitmap.Config getBitmapConfig() {
		return bitmapConfig;
	}

	public CacheConfig setBitmapConfig(Bitmap.Config bitmapConfig) {
		this.bitmapConfig = bitmapConfig;
		return this;
	}

	public String getFileCachePath() {
		return fileCachePath;
	}

	public CacheConfig setFileCachePath(String fileCachePath) {
		this.fileCachePath = fileCachePath;
		return this;
	}

	public long getMemoryCachelimit() {
		return memoryCachelimit;
	}

	public CacheConfig setMemoryCachelimit(long memoryCachelimit) {
		this.memoryCachelimit = memoryCachelimit;
		return this;
	}

}
