package com.echuang.tianyuanhao.netutils;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;

import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.StrRes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import android.content.Context;

/**
 * 工具类
 * 
 * @ClassName: NetUtils
 * @Description: TODO Http请求封装
 * @author 蒲江
 * @date 2016-7-12 下午2:11:20
 */
public class NetUtils {
	private int count = 0;
	private AsyncHttpClient httpClient = new AsyncHttpClient();
	private PersistentCookieStore myCookieStore = null;

	// 单例模式
	private NetUtils() {
	}

	private static NetUtils instance;

	public static synchronized NetUtils getInstance() {
		if (null == instance) {
			instance = new NetUtils();
		}
		return instance;
	}

	public void post(final Context context, String url, RequestParams params,
			final HttpCallback callback) {
		post(context, url, params, null, callback);
	}

	public void cancel() {
		// 取消所有请求。如果出现问题请注意这里
		httpClient.cancelAllRequests(true);
	}

	/***
	 * Http异步请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param context
	 *            当前对象
	 * @param params
	 *            请求参数
	 * @param callback
	 *            回调接口
	 * @return
	 */
	public void post(final Context context, String url, RequestParams params,
			String contentType, final HttpCallback callback) {
		if (count > 65534) {
			count = 0;
		}
		count++;
		myCookieStore = new PersistentCookieStore(context);
		httpClient.setCookieStore(myCookieStore);
		LogAPP.i("pj:" + count, url + "?" + params.toString());
		httpClient.post(context, url, params, contentType,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						LogAPP.i("pj:" + count, new String(responseBody));
						// 请求成功回调接口返回 请求数据
						callback.onSuccess(new String(responseBody));
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// 请求失败回调接口返回 请求数据
						// Logz.doLog("ztrong:" + count, new
						// String(responseBody),
						// Logz.W);
						error.printStackTrace();
						if (responseBody == null) {
							responseBody = new byte[] {};
						}
						callback.onFailure(new String(responseBody), statusCode);
						// 根据ID移除请求的Http对象
					}
				});
	}

	public CookieStore getCookieStore() {
		return myCookieStore;
	}

	/**
	 * CallBack
	 * 
	 * @author Administrator
	 * 
	 */
	public interface HttpCallback {
		public void onSuccess(String content);

		public void onFailure(String content, int statusCode);
	}

	public RequestParams getParams(String url, String message) {
		RequestParams params = new RequestParams();
		params.put(StrRes.message, message);
		return params;
	}
}
