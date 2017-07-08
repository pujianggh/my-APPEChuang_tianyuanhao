package com.echuang.tianyuanhao.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.base.BaseWebFragment.OnWebViewLoadListener;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.utils.BaseWebViewClient;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnJsCallback;
import com.echuang.tianyuanhao.utils.BaseWebViewClient.OnTelListener;
import com.echuang.tianyuanhao.utils.FileUtils;
import com.echuang.tianyuanhao.utils.JavaScriptCallBackListener;
import com.echuang.tianyuanhao.utils.JavaScriptInterface;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.view.CustomWebView;

/**
 * 通用Web加载
 * 
 * @ClassName: DynamicFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2015-7-31 下午2:01:59
 */
public class CommonWebFragment extends BaseFragment {
	public static CommonWebFragment instance = null;
	protected View parentView;
	private WebView mWebView;
	private BaseWebViewClient mBaseWebViewClient;
	protected JavaScriptInterface javaScriptInterface;
	private JavaScriptCallBackListener backListener;
	protected PullToRefreshLayout pullToRefreshLayout;
	protected OnWebViewLoadListener onWebViewLoadListener;

	private TextView textView_title;
	private ImageButton imageButton_left;

	private LinearLayout ll_failed;
	private Button bt_refreash;
	private String first_url = "";
	private String temp_url = "";
	private int temp_cout = 0;
	private boolean isFirst = true;
	private String mURL = "http://app.ztrong.com/app/about2.htm";
	private String mTitle = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		Bundle bundle = getArguments();
		if (bundle != null) {
			mURL = bundle.getString(StrRes.url);
			mTitle = bundle.getString(StrRes.title);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.fr_common_web, container, false);
		backListener = new JavaScriptCallBackListener(getActivity());
		initView();
		return parentView;
	}

	@Override
	protected void initView() {
		mWebView = (CustomWebView) parentView
				.findViewById(R.id.webView_content);
		pullToRefreshLayout = (PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view);
		textView_title = (TextView) parentView
				.findViewById(R.id.textView_title);
		imageButton_left = (ImageButton) parentView
				.findViewById(R.id.imageButton_left);
		imageButton_left.setVisibility(View.INVISIBLE);
		textView_title.setText(mTitle);
		ll_failed = (LinearLayout) parentView
				.findViewById(R.id.layout_loadingfailed);
		bt_refreash = (Button) parentView.findViewById(R.id.button_refresh);
		bt_refreash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_failed.setVisibility(View.GONE);
				mWebView.reload();
			}
		});
		pullToRefreshLayout = ((PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view));
		pullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				mWebView.reload();
			}
		});
		initWebView();
	}

	/**
	 * Web参数初始化
	 * 
	 * @Title: initWebView
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
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
		// syncCookies(mWebView, this, url);/**与httpClient的cookie同步*/
		mWebView.loadUrl(mURL);
		javaScriptInterface.setCallBackListener(backListener);
	}

	@Override
	protected void back() {
	}

	@Override
	protected void next() {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mWebView != null) {
			mWebView.destroy();
		}
	}

	/**
	 * 监听返回按钮
	 * 
	 * @Title: onKeyDown
	 * @Description: TODO
	 * @param @param keyCode
	 * @param @param event
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView.getUrl() != null
					&& !mWebView.getUrl().equals(first_url)
					&& !first_url.equals("")
					&& !first_url.equals("about:blank")
					&& !mWebView.getUrl().equals("about:blank")
					&& !first_url.contains("error_404") && temp_cout < 1) {
				mWebView.goBack();
			}
			return true;
		}
		return onKeyDown(keyCode, event);
	}
}
