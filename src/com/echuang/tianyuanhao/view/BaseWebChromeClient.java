package com.echuang.tianyuanhao.view;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * WebChromeClient类
 * 
 * @ClassName: BaseWebChromeClient
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 下午1:41:13
 */
public class BaseWebChromeClient extends WebChromeClient {

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		return super.onJsAlert(view, url, message, result);
	}

	@Override
	public boolean onJsConfirm(WebView view, String url, String message,
			JsResult result) {
		return super.onJsConfirm(view, url, message, result);
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, JsPromptResult result) {
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}

}
