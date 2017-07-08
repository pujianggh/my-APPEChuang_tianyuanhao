package com.echuang.tianyuanhao.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

/**
 * 文件工具类
 * 
 * @ClassName: FileUtils
 * @Description: TODO 文件处理工具类
 * @author 蒲江
 * @date 2016-7-12 下午5:02:03
 */
public class FileUtils {

	public static final String ROOTPATH = GetRootPath();
	/**
	 * 储存数据 ，如txt文件、sqlite数据库等
	 */
	public static final String DATAPATH = ROOTPATH + "/data";
	/**
	 * 音频文件
	 */
	public static final String AUDIOPATH = ROOTPATH + "/audio";
	/**
	 * 储存图片资源路径
	 */
	public static final String FRAMEPATH = ROOTPATH + "/file";
	/**
	 * 网络缓存
	 */
	public static final String WEBPATH = ROOTPATH + "/webcache";

	/**
	 * 获取本地sd卡路径
	 * @Title: GetRootPath
	 * @Description: TODO 
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String GetRootPath() {
		if (isSdcardExist()) {
			return Environment.getExternalStorageDirectory().getPath()
					+ "/Echuang_tianyuanhao";
		} else {
			return "/sdcard" + "/Echuang_tianyuanhao";
		}
	}

	/**
	 * TODO：判断SD是否可以
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:22:13
	 * @version 1.0
	 * @param isSdcardExist
	 * @return boolean
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * TODO：判断name名字的信息是否在路径path下、是返回ture
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年7月7日 下午2:31:51
	 * @version 1.0
	 * @param isBitmap
	 * @return boolean
	 */
	public static boolean isNameExistPath(String path, String name) {
		File file = new File(path, name);
		return file.exists();
	}

	/**
	 * TODO：创建根目录
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:22:41
	 * @version 1.0
	 * @param createDirFile
	 * @return void
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * TODO：创建文件
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:24:26
	 * @version 1.0
	 * @param createNewFile
	 * @return File
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * TODO：删除文件夹
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:24:56
	 * @version 1.0
	 * @param delFolder
	 * @return void
	 */
	public static void deleteFolder(String folderPath) {
		deleteAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * TODO：删除所有文件
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:25:27
	 * @version 1.0
	 * @param delAllFile
	 * @return void
	 */
	public static void deleteAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFile(path + "/" + tempList[i]);
				deleteFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * TODO：获取文件的Uri
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:26:54
	 * @version 1.0
	 * @param getUriFromFile
	 * @return Uri
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}

	/**
	 * TODO：换算文件大小
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:27:25
	 * @version 1.0
	 * @param formatFileSize
	 * @return String
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "未知大小";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * TODO：写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:36:09
	 * @version 1.0
	 * @param writeToTxtFile
	 * @return void
	 */
	public static void writeToTxtFile(Context context, String fileName,
			String content) {
		if (content == null)
			content = "";
		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO：读取文本文件
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:38:16
	 * @version 1.0
	 * @param readFromTxtFile
	 * @return String
	 */
	public static String readFromTxtFile(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * TODO：读取数据流信息
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:39:54
	 * @version 1.0
	 * @param readInStream
	 * @return String
	 */
	private static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}
			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return null;
	}

	/**
	 * TODO：获取文件大小
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:44:56
	 * @version 1.0
	 * @param getFileSize
	 * @return long
	 */
	public static long getFileSize(String filePath) {
		long size = 0;
		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * TODO：获取目录文件大小
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:49:57
	 * @version 1.0
	 * @param getDirFileSize
	 * @return long
	 */
	public static long getDirFileSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				/**
				 * 递归调用继续统计
				 */
				dirSize += getDirFileSize(file);
			}
		}
		return dirSize;
	}

	/**
	 * TODO：获取目录文件个数
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:50:32
	 * @version 1.0
	 * @param getFileList
	 * @return long
	 */
	public long getDirFileNum(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归
				count = count + getDirFileNum(file);
				count--;
			}
		}
		return count;
	}

	/**
	 * TODO：计算SD卡的剩余空间
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:55:24
	 * @version 1.0
	 * @param getFreeSdcardSpace
	 * @return long
	 */
	public static long getFreeSdcardSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return freeSpace;
	}

	/**
	 * TODO：检查是否安装SD卡
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:56:56
	 * @version 1.0
	 * @param checkSaveLocationExists
	 * @return boolean
	 */
	public static boolean checkLocationExistSdcard() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * TODO： 删除目录(包括：目录里的所有文件)
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月24日 上午11:59:17
	 * @version 1.0
	 * @param deleteDirAllFile
	 * @return boolean
	 */
	public static boolean deleteDirAllFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();
		if (!fileName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * TODO ：获取文件里的信息 返回list数据
	 * 
	 * @author jiang_pu
	 * @created 2014 2014年6月19日 下午2:07:35
	 * @version 1.0
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static List<String> getSqlFileStr(Context context, String filePath) {
		StringBuffer sb = new StringBuffer();
		List<String> sqlStrList = new ArrayList<String>();
		try {
			String line = "";
			AssetManager manager = context.getResources().getAssets();
			InputStream iStream = manager.open(filePath);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream, "UTF-8"), 8192);
			while ((line = br.readLine()) != null) {
				if (line.trim().length() == 0
						&& sb.toString().trim().length() > 0) {
					sqlStrList.add(sb.toString().trim());
					sb = new StringBuffer();
				} else {
					sb.append(line.trim());
				}
			}
			if (sb.toString().trim().length() > 0) {
				sqlStrList.add(sb.toString().trim());
			}
			br.close();
			iStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlStrList;
	}
}
