package com.echuang.tianyuanhao.utils;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * webview设置
 * 
 * @ClassName: BaseWebViewClient
 * @Description: TODO webview设置
 * @author 蒲江
 * @date 2016-7-11 上午11:50:54
 */
public class BaseWebViewClient extends WebViewClient {

	private OnJsCallback onJsCallback;
	/**
	 * 页面是否加载完成
	 */
	private boolean isLoadComplete = false;
	/**
	 * 页面是否加载失败
	 */
	private boolean isFailed = false;

	public BaseWebViewClient() {
		super();
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		LogAPP.i("shouldOverrideUrlLoading", "" + url);
		// 判断是否为电话
		if (url.contains("tel:")) {
			LogAPP.i("CustomWebView", "url.contains tel:");
			String tel = "";
			try {
				tel = url.substring(url.indexOf(":") + 1);
			} catch (Exception e) {
				e.printStackTrace();
				tel = "";
			}
			if (onTelListener != null && !"".equals(tel)) {
				onTelListener.OnTel(tel);
			}
			return true;
		}
		// 判断是否有ios标签（这里会有一个问题，就是正常含有ios的链接会打不开）
		if (!url.contains("ios=")) {
			view.loadUrl(url);
		} else {
			LogAPP.i("CustomWebView", "url.contains ios");
			return true;
		}
		return false;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		LogAPP.i("onPageFinished", url);
		if (!view.getSettings().getLoadsImagesAutomatically()) {
			view.getSettings().setLoadsImagesAutomatically(true);
		}
		isFailed = false;
		// 执行自定义方法如js调用
		isLoadComplete = true;
		if (onJsCallback != null) {
			onJsCallback.callBack(url);
		}
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		isFailed = false;
		super.onPageStarted(view, url, favicon);
		isLoadComplete = false;
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			android.net.http.SslError error) {
		handler.proceed();
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		isFailed = true;
		LogAPP.i("onReceivedError", failingUrl);
		if (onJsCallback != null) {
			onJsCallback.callFailed(failingUrl);
		}
		// view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}

	@Override
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		return super.shouldOverrideKeyEvent(view, event);
	}

	public OnJsCallback getOnJsCallback() {
		return onJsCallback;
	}

	public void setOnJsCallback(OnJsCallback onJsCallback) {
		this.onJsCallback = onJsCallback;
	}

	public boolean isLoadComplete() {
		return isLoadComplete;
	}

	public void setLoadComplete(boolean isLoadComplete) {
		this.isLoadComplete = isLoadComplete;
	}

	public boolean isFailed() {
		return isFailed;
	}

	public void setFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

	public void setOnTelListener(OnTelListener onTelListener) {
		this.onTelListener = onTelListener;
	}

	public interface OnJsCallback {
		void callBack(String url);

		void callFailed(String url);
	}

	public interface OnTelListener {
		void OnTel(String tel);
	}

	private OnTelListener onTelListener;

}
