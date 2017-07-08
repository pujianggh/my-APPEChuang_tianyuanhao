package com.echuang.tianyuanhao.activity;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.utils.BaseWebViewClient;
import com.echuang.tianyuanhao.utils.FileUtils;
import com.echuang.tianyuanhao.utils.JavaScriptCallBackListener;
import com.echuang.tianyuanhao.utils.JavaScriptInterface;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnJsCallback;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnTelListener;
import com.echuang.tianyuanhao.view.CustomWebView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayActivity extends Activity implements OnClickListener {

	private String mURL = "http://gotyh.com/tianyuanadmin/video.html";

	private CustomWebView mWebView;
	private JavaScriptInterface javaScriptInterface;
	private BaseWebViewClient mBaseWebViewClient;

	private JavaScriptCallBackListener backListener;

	private ImageButton ivBack;

	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lb_video);
		initView();

	}

	private void initView() {

		// 初始化头
		ivBack = (ImageButton) findViewById(R.id.iv_shop_card_detail);
		tvTitle = (TextView) findViewById(R.id.tv_detail_title);
		tvTitle.setText("");
		ivBack.setOnClickListener(this);

		mWebView = (CustomWebView) findViewById(R.id.webView_video);
		backListener = new JavaScriptCallBackListener(this);
		initWebView();

	}

	@SuppressLint("SetJavaScriptEnabled")
	protected void initWebView() {
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		// 设置JS可用
		webSettings.setAllowFileAccess(true);
		// 自适应屏幕
		// webSettings.setUseWideViewPort(true);
		// webSettings.setLoadWithOverviewMode(true);
		// webSettings.setSupportZoom(true);//是否支持缩放
		// webSettings.setBuiltInZoomControls(true);//显示放大缩小按钮
		// JAVAScript脚本
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSaveFormData(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		// 开启 DOM storage API 功能
		webSettings.setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webSettings.setDatabaseEnabled(true);
		String cacheDirPath = FileUtils.WEBPATH;
		// 设置数据库缓存路径
		webSettings.setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		webSettings.setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		webSettings.setAppCacheEnabled(true);

		mWebView.requestFocus();
		javaScriptInterface = new JavaScriptInterface();
		mWebView.addJavascriptInterface(javaScriptInterface, "android");
		webSettings.setDefaultTextEncodingName("UTF-8");
		mBaseWebViewClient = new BaseWebViewClient();
		mBaseWebViewClient.setOnJsCallback(new OnJsCallback() {
			@Override
			public void callBack(String url) {

			}

			@Override
			public void callFailed(String url) {

			}
		});
		mBaseWebViewClient.setOnTelListener(new OnTelListener() {

			@Override
			public void OnTel(String tel) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ tel));
				startActivity(intent);

			}
		});
		mWebView.setWebViewClient(mBaseWebViewClient);
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
			}
		});
		// syncCookies(mWebView, this, url);/**与httpClient的cookie同步*/
		mWebView.loadUrl(mURL);
		javaScriptInterface.setCallBackListener(backListener);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mWebView.destroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			mWebView.destroy();
			finish();
			break;

		default:
			break;
		}

	}

}
