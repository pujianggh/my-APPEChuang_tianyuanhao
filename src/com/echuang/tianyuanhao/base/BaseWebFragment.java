package com.echuang.tianyuanhao.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.LoginActivity;
import com.echuang.tianyuanhao.activity.MainActivity;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.utils.BaseWebViewClient;
import com.echuang.tianyuanhao.utils.JavaScriptInterface;
import com.echuang.tianyuanhao.utils.JavaScriptInterface.CallBackListener;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.echuang.tianyuanhao.view.BaseWebChromeClient;
import com.echuang.tianyuanhao.view.CustomWebView;

/**
 * BaseWebFragment基本类
 * 
 * @ClassName: BaseWebFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午11:23:31
 */
@SuppressLint("HandlerLeak")
public class BaseWebFragment extends BaseFragment {
	public static BaseWebFragment instance = null;
	protected View parentView;
	protected CustomWebView webview;
	protected MainActivity activity;

	protected JavaScriptInterface javaScriptInterface;
	protected BaseWebViewClient pwc;

	protected PullToRefreshLayout pullToRefreshLayout;
	protected static final String APP_CACAHE_DIRNAME = "/Echuang/webcache";
	protected OnWebViewLoadListener onWebViewLoadListener;

	protected LinearLayout ll_failed;
	protected Button bt_refreash;

	private Bundle bundle;
	public String mUrl = "";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		bundle = getArguments();
		if (bundle != null) {
			mUrl = bundle.getString(StrRes.url);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fr_web, container, false);
		webview = (CustomWebView) parentView.findViewById(R.id.webView_content);
		pullToRefreshLayout = (PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view);
		//pullToRefreshLayout.setEnablePullTorefresh(false);
		initView();
		return parentView;
	}

	protected void initView() {
		initWebView();
	}

	@SuppressLint({ "SetJavaScriptEnabled" })
	protected void initWebView() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		// 设置JS可用
		webSettings.setAllowFileAccess(true);
		webSettings.setLoadWithOverviewMode(true);
		// 自适应屏幕
		// webSettings.setUseWideViewPort(true);
		// JAVAScript脚本
		//webSettings.setSupportZoom(true);//支持缩放
		//webSettings.setBuiltInZoomControls(true);//设置出现缩放工具 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSaveFormData(true);
		
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		// 开启 DOM storage API 功能
		webSettings.setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webSettings.setDatabaseEnabled(true);
		String cacheDirPath = getActivity().getFilesDir().getAbsolutePath()
				+ APP_CACAHE_DIRNAME;
		// 设置数据库缓存路径
		webSettings.setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		webSettings.setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		webSettings.setAppCacheEnabled(true);

		webview.requestFocus();
		javaScriptInterface = new JavaScriptInterface();
		webview.addJavascriptInterface(javaScriptInterface, "android");
		javaScriptInterface.setCallBackListener(new CallBackListener() {

			@Override
			public void callBack(String msg, int type) {
				switch (type) {
				case JavaScriptInterface.JS_returnLogoutMsg:
					// 保存密码
					sharedPreUtils.savePassWord("");
					// 保存登录
					sharedPreUtils.saveIsLogin(false);
					break;
				case JavaScriptInterface.JS_returnLoginPageMsg:
					startActivityForResult(new Intent(getActivity(),
							LoginActivity.class), 11);
					break;
				case JavaScriptInterface.JS_returnCanRefresh:
					boolean can = true;
					if (msg.equals("false")) {
						can = false;
					} else {
						can = true;
					}
					pullToRefreshLayout.setEnablePullTorefresh(can);
					break;
				}

			}
		});
		webSettings.setDefaultTextEncodingName("UTF-8");
		pwc = new BaseWebViewClient();
		webview.setWebViewClient(pwc);
		if (!TextUtils.isEmpty(mUrl)) {
			webview.loadUrl(mUrl);
		}
		webview.setWebChromeClient(new BaseWebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
			}
		});
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		if (Utils.isFastClick()) {
			return;
		}
	}

	@Override
	protected void back() {
	}

	@Override
	protected void next() {
	}

	protected void reFresh(int type) {
		pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (webview != null) {
			webview.destroy();
		}
	}

	public interface OnWebViewLoadListener {
		void OnWebLoad(String url, int type);
	}

	public BaseWebFragment setOnWebViewLoadListener(
			OnWebViewLoadListener onWebViewLoadListener) {
		this.onWebViewLoadListener = onWebViewLoadListener;
		return this;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webview.getUrl() != null
					&& !webview.getUrl().equals("about:blank")) {
				webview.goBack();
			}
			return true;
		}
		return onKeyDown(keyCode, event);
	}

}
