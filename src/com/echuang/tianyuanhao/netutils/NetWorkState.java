package com.echuang.tianyuanhao.netutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.echuang.tianyuanhao.utils.LogAPP;

/**
 * 检查网络状态
 * 
 * @ClassName: NetWorkState
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 下午2:10:19
 */
public class NetWorkState {

	/**
	 * 判断是否连接网络
	 * 
	 * @Title: isNetworkAvailable
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			LogAPP.i("NetWorkState", "Unavailabel");
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						LogAPP.i("NetWorkState", "Availabel");
						return true;
					}
				}
			}
		}
		return false;
	}
}
