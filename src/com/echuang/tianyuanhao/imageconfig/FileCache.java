package com.echuang.tianyuanhao.imageconfig;

import android.content.Context;

import java.io.File;

/**
 * 图片缓存文件类
 * 
 * @ClassName: FileCache
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午10:52:24
 */
public class FileCache {

	/**
	 * 文件缓存保存目录
	 */
	private File cacheDir;

	public FileCache(Context context, String cachePath) {
		// 如果有SD卡则在SD卡中建一个目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(cachePath);
		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	/**
	 * 获取指定文件缓存
	 * 
	 * @param url
	 * @return
	 */
	public File getFile(String url) {
		// 将url的hashCode作为缓存的文件名
		String filename = String.valueOf(url.hashCode());
		// Another possible solution
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;
	}

	/**
	 * 清除文件缓存
	 */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			f.delete();
		}
	}

}
