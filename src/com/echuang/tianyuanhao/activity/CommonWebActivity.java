package com.echuang.tianyuanhao.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.utils.BaseWebViewClient;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnJsCallback;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnTelListener;
import com.echuang.tianyuanhao.utils.JavaScriptCallBackListener;
import com.echuang.tianyuanhao.utils.JavaScriptInterface;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.StrRes;

/**
 * 通用web页面
 * 
 * @ClassName: CommonWebActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 下午4:30:57
 */
public class CommonWebActivity extends BaseSwipeBackActivity {
	private WebView mWebView;
	private JavaScriptInterface javaScriptInterface;
	private BaseWebViewClient mBaseWebViewClient;
	private String url = "";
	private static final String APP_CACAHE_DIRNAME = "/Ztrong/webcache";
	private LinearLayout ll_failed;
	private Button bt_refreash;
	private PullToRefreshLayout pullToRefreshLayout;
	private String first_url = "";
	private String temp_url = "";
	private int temp_cout = 0;
	private boolean isFirst = true;
	private JavaScriptCallBackListener backListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_common_web);
		url = getIntent().getStringExtra(StrRes.url);
		backListener = new JavaScriptCallBackListener(((Activity) this));
		initView();
	}

	@Override
	protected void back() {
		if (mWebView.getUrl() != null && !mWebView.getUrl().equals(first_url)
				&& !first_url.equals("") && !first_url.equals("about:blank")
				&& !mWebView.getUrl().equals("about:blank")
				&& !first_url.contains("error_404") && temp_cout < 1) {
			mWebView.goBack();
			if (temp_url.equals(mWebView.getUrl())) {
				temp_cout++;
			} else {
				temp_cout = 0;
			}
		} else {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
	}

	@Override
	protected void initView() {
		initTitle(getIntent().getStringExtra(StrRes.title), "", 0);
		ll_failed = (LinearLayout) findViewById(R.id.layout_loadingfailed);
		bt_refreash = (Button) findViewById(R.id.button_refresh);
		bt_refreash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_failed.setVisibility(View.GONE);
				mWebView.reload();

			}
		});
		pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				mWebView.reload();
			}
		});
		if (getIntent().getIntExtra(StrRes.type, 0) == 2) {
			pullToRefreshLayout.setEnablePullTorefresh(false);
		}
		initWebView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	protected void initWebView() {
		mWebView = (WebView) findViewById(R.id.webView_content);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		// 设置JS可用
		webSettings.setAllowFileAccess(true);
		// 自适应屏幕
		// webSettings.setUseWideViewPort(true);
		// webSettings.setLoadWithOverviewMode(true);
		//webSettings.setSupportZoom(true);//是否支持缩放
		//webSettings.setBuiltInZoomControls(true);//显示放大缩小按钮
		// JAVAScript脚本
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSaveFormData(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		// 开启 DOM storage API 功能
		webSettings.setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webSettings.setDatabaseEnabled(true);
		String cacheDirPath = getFilesDir().getAbsolutePath()
				+ APP_CACAHE_DIRNAME;
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
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				if (isFirst) {
					isFirst = false;
					first_url = url;
				}
				temp_url = url;
				LogAPP.i("pj" + temp_cout, temp_url);
			}

			@Override
			public void callFailed(String url) {
				if (ll_failed != null) {
					ll_failed.setVisibility(View.VISIBLE);
				}
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
				stopDialog();
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
		// 与httpClient的cookie同步
		//syncCookies(mWebView, this, url);
		mWebView.loadUrl(url);
		javaScriptInterface.setCallBackListener(backListener);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imageButton_left:
			back();
			break;
		case R.id.imageButton_right:
		case R.id.button_right:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
