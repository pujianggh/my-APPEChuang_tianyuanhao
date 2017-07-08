package com.echuang.tianyuanhao.activity;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.MainFragmentTabAdapter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.fragment.AboutFragment;
import com.echuang.tianyuanhao.fragment.DynamicFragment;
import com.echuang.tianyuanhao.fragment.EarningFragment;
import com.echuang.tianyuanhao.fragment.MainHomeFragment;
import com.echuang.tianyuanhao.fragment.MyFragment;
import com.echuang.tianyuanhao.utils.StrRes;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheEntity;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.handler.UMWXHandler;

/**
 * 主界面
 * 
 * @ClassName: MainActivity
 * @Description: TODO
 * @author Libao
 * @date 2016-8-30 上午11:40:42
 */
public class MainActivity extends BaseFragmentActivity {
	public static final String RECEIVER_ISFORCHECKTAB = "com.e-chuang.RECEIVER_ISFORCHECKTAB";
	private MainFragmentTabAdapter tabAdapter;
	private boolean isExit;
	private boolean[] showtitles;
	private int[] levels;
	public RadioGroup mTabRg;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	private MainHomeFragment mMainHomeFragment = new MainHomeFragment();// 首页
	private EarningFragment mEarningFragment = new EarningFragment();// 收益
	private DynamicFragment mDynamicFragment = new DynamicFragment();// 动态
	public AboutFragment mAboutFragment = new AboutFragment();// 关于
	private MyFragment MyFragment = new MyFragment();// 我的

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_main);
		// wxfb7a2a673cb7afca
		PlatformConfig.setWeixin("wxfb7a2a673cb7afca",
				"460d4c3de34ca408a025b1cd2b97ae3e");
		// 微信 appid appsecret
		PlatformConfig.setSinaWeibo("3921700954",
				"04b48b094faeb16683c32669824ebdad");
		// 新浪微博 appkey appsecret

		IntentFilter intentFilter = new IntentFilter(RECEIVER_ISFORCHECKTAB);
		broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
		initView();
		// 友盟分享
		uMengshare();
		// 对okhttputils的全局初始化
		initOkhttp();

	}

	private void uMengshare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initView() {
		Bundle mAboutBundle = new Bundle();
		//mAboutBundle.putString(StrRes.url, "https://www.umeng.com");
		mAboutBundle.putString(StrRes.url, "file:///android_asset/img.html");
		mAboutBundle.putString(StrRes.title, "关于");
		mAboutFragment.setArguments(mAboutBundle);

		fragments.add(mMainHomeFragment);
		fragments.add(mEarningFragment);
		fragments.add(mDynamicFragment);
		fragments.add(mAboutFragment);
		fragments.add(MyFragment);
		showtitles = new boolean[fragments.size()];
		levels = new int[fragments.size()];
		for (int i = 0; i < showtitles.length; i++) {
			showtitles[i] = true;
			levels[i] = 0;
		}
		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		checkTab(R.id.tab_rb_1);
		if (tabAdapter == null) {
			tabAdapter = new MainFragmentTabAdapter(this, fragments,
					R.id.tab_content, mTabRg);
		}

	}

	/**
	 * 选择按钮
	 * 
	 * @Title: checkTab
	 * @Description: TODO
	 * @param @param checkedId
	 * @return void
	 * @throws
	 */
	public void checkTab(int checkedId) {
		for (int i = 0; i < mTabRg.getChildCount(); i++) {
			if (mTabRg.getChildAt(i).getId() == checkedId) {
				((RadioButton) mTabRg.getChildAt(i)).setChecked(true);
			}
		}
	}

	@Override
	protected void next() {
	}

	@Override
	protected void back() {
		exit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), getString(R.string.exit),
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			AppAppliction.getInstance().exit();
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		/**
		 * @Todo
		 */

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (broadcastManager != null)
			broadcastManager.unregisterReceiver(broadcastReceiver);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(MainActivity.RECEIVER_ISFORCHECKTAB)) {
				switch (intent.getIntExtra(StrRes.type, 1)) {
				case 1:
					checkTab(R.id.tab_rb_1);
					break;
				case 2:
					checkTab(R.id.tab_rb_2);
					break;
				case 3:
					checkTab(R.id.tab_rb_3);
					break;
				case 4:
					checkTab(R.id.tab_rb_4);
					break;

				}
			}
		}
	};

	private void initOkhttp() {
		
// ---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//		HttpHeaders headers = new HttpHeaders();
//		headers.put("commonHeaderKey1", "commonHeaderValue1"); // header不支持中文
//		headers.put("commonHeaderKey2", "commonHeaderValue2");
//		HttpParams params = new HttpParams();
//		params.put("commonParamsKey1", "commonParamsValue1"); // param支持中文,直接传,不要自己编码
//		params.put("commonParamsKey2", "这里支持中文参数");
// -----------------------------------------------------------------------------------//
		
		// 必须调用初始化
		OkHttpUtils.init(getApplication());
		// 以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
		// 好处是全局参数统一,特定请求可以特别定制参数
		try {
			// 以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
			OkHttpUtils.getInstance()
			// 打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
					.debug("OkHttpUtils")

					// 如果使用默认的 60秒,以下三行也不需要传
					.setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的连接超时时间
					.setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的读取超时时间
					.setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的写入超时时间

					// 可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍
					// https://github.com/jeasonlzy0216/
					.setCacheMode(CacheMode.NO_CACHE)
					// 可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
					.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
					// 如果不想让框架管理cookie,以下不需要
					// .setCookieStore(new MemoryCookieStore())
					// //cookie使用内存缓存（app退出后，cookie消失）
					.setCookieStore(new PersistentCookieStore()); // cookie持久化存储，如果cookie不过期，则一直有效
					// 可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
					// .setCertificates() //方法一：信任所有证书
					// .setCertificates(getAssets().open("srca.cer"))
					// //方法二：也可以自己设置https证书
					// .setCertificates(getAssets().open("aaaa.bks"), "123456",
					// getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密
					// 可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
					// .addInterceptor(new Interceptor() {
					// @Override
					// public Response intercept(Chain chain) throws IOException
					// {
					// return chain.proceed(chain.request());
					// }
					// })

					// 这两行同上,不需要就不要传
					//.addCommonHeaders(headers) // 设置全局公共头
					//.addCommonParams(params); // 设置全局公共参数
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
