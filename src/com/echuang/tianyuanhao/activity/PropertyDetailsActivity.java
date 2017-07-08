package com.echuang.tianyuanhao.activity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader.ForceLoadContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.DetailPagerAdapter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.model.ADInfo;
import com.echuang.tianyuanhao.model.LBDetailAssetsModel;
import com.echuang.tianyuanhao.model.LBMyFragmentModel;
import com.echuang.tianyuanhao.model.LBMainAssetsModel.Resultdata;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;
import com.echuang.tianyuanhao.utils.SPUtils;
import com.echuang.tianyuanhao.view.SharePopupWindow;
import com.echuang.tianyuanhao.view.VerticalScrollView;
import com.echuang.tianyuanhao.view.pager.CycleViewPager;
import com.echuang.tianyuanhao.view.pager.CycleViewPager.ImageCycleViewListener;
import com.echuang.tianyuanhao.view.pager.ViewFactory;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 首页-房产详情
 * 
 */
public class PropertyDetailsActivity extends BaseSwipeBackActivity implements
		OnClickListener {
	private static final String TAG = "PropertyDetailsActivity";
	private Context mContext = this;
	private SharePopupWindow mSharePopupWindow = null;
	private RelativeLayout rl_mtitle;
	private VerticalScrollView sl_detail;
	private ImageButton btn_back;
	private ImageButton btn_shopcard;
	private ImageButton btn_share;
	private LinearLayout ll_knowmore;
	private LinearLayout ll_personservice;
	private Button addbtnshopcard;
	private ViewPager basevip;
	private int mRid;
	//网络数据处理
	protected static final int MAIN_DETAIL = 0;
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case MAIN_DETAIL:
					final LBDetailAssetsModel detailAssetsModel = (LBDetailAssetsModel) msg.obj;
					//获取轮播图的地址  放入集合
					
					//imageUrlLists.add(LBUrls.ALL_IMG_PRE_URL+detailAssetsModel.main.room_imgs);
					//System.out.print("LBDetailAssetsModel"+imageUrlLists.size());
					SPUtils.put(mContext, "recycle_url", LBUrls.ALL_IMG_PRE_URL+detailAssetsModel.main.room_imgs);
					
					
					
					
					String roomDecription = detailAssetsModel.main.room_introduce;
					System.out.print("handleMessage詳情model："+roomDecription);
					//對詳情頁信息進行設置
					if (detailAssetsModel!=null) {
						tvDetailPrice.setText(detailAssetsModel.main.room_pin);
						tvAssetsDetailAddress.setText(detailAssetsModel.main.room_address);
						//净回報率
						tvDetailPropertyRate.setText(detailAssetsModel.main.room_all);
						//資產升值
						tvDetailRate.setText(detailAssetsModel.main.room_value);
						//月租金
						tvDetailRent.setText(detailAssetsModel.main.room_rent);
						//租金漲幅
						tvDetailRentUp.setText(detailAssetsModel.main.room_float);
						//毛回報率
						tvDetailRoughRate.setText(detailAssetsModel.main.room_return);
						//
						tvDetailYearRate.setText(detailAssetsModel.main.room_years);
						
						//房產特色
						tvFcts.setText(detailAssetsModel.kfs.text);
						//房產信息
						tvHouseMessage1.setText(detailAssetsModel.a.get(0).strA);
						tvHouseMessage2.setText(detailAssetsModel.a.get(1).strA);
						tvHouseMessage3.setText(detailAssetsModel.a.get(2).strA);
						tvHouseMessage4.setText(detailAssetsModel.a.get(3).strA);
						tvHouseMessage5.setText(detailAssetsModel.a.get(4).strA);
						tvHouseMessage6.setText(detailAssetsModel.a.get(5).strA);
						tvHouseMessage7.setText(detailAssetsModel.a.get(6).strA);
						tvHouseMessage8.setText(detailAssetsModel.a.get(7).strA);
						tvHouseMessage9.setText(detailAssetsModel.a.get(8).strA);
						//租约信息
						tvHouseRent1.setText(detailAssetsModel.l.get(0).strL);
						tvHouseRent2.setText(detailAssetsModel.l.get(1).strL);
						tvHouseRent3.setText(detailAssetsModel.l.get(2).strL);
						tvHouseRent4.setText(detailAssetsModel.l.get(3).strL);
						tvHouseRent5.setText(detailAssetsModel.l.get(4).strL);
						tvHouseRent6.setText(detailAssetsModel.l.get(5).strL);
						//pdf 设置
						tv_house_report.setText(detailAssetsModel.pdf.anthonykeanetext);
						tv_house_report1.setText(detailAssetsModel.pdf.taxknowledgetext);
						rlReport.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent pdfIntentA = new Intent(PropertyDetailsActivity.this,PdfAActivity.class);
								Bundle pdfBundleA = new Bundle();
								//把rid传到详情页
								pdfBundleA.putString("pdfUrla",detailAssetsModel.pdf.anthonykeane);
								pdfIntentA.putExtras(pdfBundleA);
								startActivity(pdfIntentA);
								
							}
						});
						rlReport1.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Intent pdfIntentB = new Intent(PropertyDetailsActivity.this,PdfBActivity.class);
								Bundle pdfBundleB = new Bundle();
								//把rid传到详情页
								pdfBundleB.putString("pdfUrlb",detailAssetsModel.pdf.taxknowledge);
								pdfIntentB.putExtras(pdfBundleB);
								startActivity(pdfIntentB);
								
							}
						});
						//产权所属
						ImageLoaderHelper.displayImage(LBUrls.ALL_IMG_PRE_URL+detailAssetsModel.pdf.country, ivCompany);
						 tvCompany.setText(detailAssetsModel.pdf.countrytext);
						//物业管理方
						ImageLoaderHelper.displayImage(LBUrls.ALL_IMG_PRE_URL+detailAssetsModel.kfs.img, ivCompany1);
						tvCompany1.setText(detailAssetsModel.kfs.text);
						
						
						
						
						
						
						
						
						
						
						
					}
				break;
			}
		}
	};
	

	// 百度地图要用到的变量
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	

	// 轮播图数据
	private com.echuang.tianyuanhao.view.pager.CycleViewPager mCycleViewPager;// 标底循环滚动
	// 轮播图的页面数量
	private static final int SIZE = 5;
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private List<ADInfo> adInfos = new ArrayList<ADInfo>();
	
	
	private String[] imageUrls = {
			"http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg",
			"http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg",
			"http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg",
			 };
	private RelativeLayout rlPropertyDetail1;
	private RelativeLayout rlPropertyDetail2;
	private RelativeLayout rlPropertyDetail3;
	private TextView tvAssetsDetailAddress;
	private TextView tvDetailPrice;
	private TextView tvDetailRent;
	private TextView tvDetailRate;
	private TextView tvDetailYearRate;
	private TextView tvDetailRoughRate;
	private TextView tvDetailPropertyRate;
	private TextView tvDetailRentUp;
	private TextView tvFcts;
	private TextView tvHouseMessage1;
	private TextView tvHouseMessage2;
	private TextView tvHouseMessage3;
	private TextView tvHouseMessage4;
	private TextView tvHouseMessage5;
	private TextView tvHouseMessage6;
	private TextView tvHouseMessage7;
	private TextView tvHouseMessage8;
	private TextView tvHouseMessage9;
	private TextView tvHouseRent1;
	private TextView tvHouseRent2;
	private TextView tvHouseRent3;
	private TextView tvHouseRent4;
	private TextView tvHouseRent5;
	private TextView tvHouseRent6;
	private TextView tv_house_report;
	private ImageView iv_house_report;
	private TextView tv_house_report0;
	private ImageView iv_house_report1;
	private TextView tv_house_report1;
	private TextView tv_house_report2;
	private RelativeLayout rlReport;
	private RelativeLayout rlReport1;
	private ImageView ivCompany;
	private TextView tvCompany;
	private ImageView ivCompany1;
	private TextView tvCompany1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);

		// 百度地图要用到的东西
		setContentView(R.layout.act_property_details);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		//定位当前位置
		//atLocalAddress();
		init();	
		
		
		//获取上个界面传来的数据
		getBundle();
		//获取网络数据
		loadDataFromNet();
		
		initView();
		configImageLoader();
		initialize();
		
	}
	

	private void getBundle() {
		String rid = getIntent().getExtras().getString("rid");
		mRid = Integer.parseInt(rid);
		System.out.print("其他页面传递过来的rid+getBundle()==================:"+rid);
	}

	private void init() {
	        //1.单例   2.工厂  3.构造器
	        MapStatus.Builder builder = new MapStatus.Builder();
	        builder.overlook(-45);
	        builder.rotate(-30);
	        builder.zoom(18);
	        builder.target(new LatLng(31.2428680000,121.4905840000));
	        //31.2428680000,121.4905840000
	        MapStatus mapStatus = builder.build();
	        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
	        mBaiduMap.setMapStatus(mapStatusUpdate);
	    }

	

	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	@SuppressLint("NewApi")
	public void initialize() {

		mCycleViewPager = (com.echuang.tianyuanhao.view.pager.CycleViewPager) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);
		
		List<String> imageUrlLists = new ArrayList<String>();
		imageUrlLists.add(SPUtils.getString(mContext, "recycle_url"));
		
		for (int i = 0; i <imageUrlLists.size() ; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrlLists.get(i));
			info.setContent("图片-->" + i);
			adInfos.add(info);
		}

		// 将最后一个ImageView添加进来
		imageViews.add(ViewFactory.getImageView(this,
				adInfos.get(adInfos.size() - 1).getUrl()));
		for (int i = 0; i < adInfos.size(); i++) {
			imageViews.add(ViewFactory.getImageView(this, adInfos.get(i)
					.getUrl()));
		}
		// 将第一个ImageView添加进来
		imageViews.add(ViewFactory.getImageView(this, adInfos.get(0).getUrl()));

		// 设置循环，在调用setData方法前调用
		mCycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		mCycleViewPager.setData(imageViews, adInfos,
				new ImageCycleViewListener() {

					@Override
					public void onImageClick(ADInfo info, int position,
							View imageView) {

						if (mCycleViewPager.isCycle()) {
							position = position - 1;
							Toast.makeText(mContext,
									"position-->" + info.getContent(),
									Toast.LENGTH_SHORT).show();
						}

					}

				});

		// 设置轮播
		mCycleViewPager.setWheel(true);

		// 设置轮播时间，默认5000ms
		mCycleViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		mCycleViewPager.setIndicatorCenter();
	}

	@Override
	protected void initView() {
		//适配网络数据
		
		tvAssetsDetailAddress = (TextView) findViewById(R.id.tv_assets_detail_address);
		tvDetailPrice = (TextView) findViewById(R.id.tv_assets_detail_price);
		tvDetailRent = (TextView) findViewById(R.id.tv_assets_detail_rent);
		tvDetailRate = (TextView) findViewById(R.id.tv_assets_detail_rate);
		tvDetailYearRate = (TextView) findViewById(R.id.tv_assets_detail_yearrate);
		tvDetailRoughRate = (TextView) findViewById(R.id.tv_assets_detail_roughrate);
		tvDetailPropertyRate = (TextView) findViewById(R.id.tv_assets_detail_propertyrate);
		tvDetailRentUp = (TextView) findViewById(R.id.tv_assets_detail_rentup);
		
		tvFcts = (TextView)findViewById(R.id.tv_fcts);
		tvHouseMessage1 = (TextView)findViewById(R.id.tv_house1_message);
		tvHouseMessage2 = (TextView)findViewById(R.id.tv_house1_message1);
		tvHouseMessage3 = (TextView)findViewById(R.id.tv_house2_message2);
		tvHouseMessage4 = (TextView)findViewById(R.id.tv_house3_message3);
		tvHouseMessage5 = (TextView)findViewById(R.id.tv_house4_message4);
		tvHouseMessage6 = (TextView)findViewById(R.id.tv_house5_message5);
		tvHouseMessage7 = (TextView)findViewById(R.id.tv_house6_message6);
		tvHouseMessage8 = (TextView)findViewById(R.id.tv_house7_message7);
		tvHouseMessage9 = (TextView)findViewById(R.id.tv_house8_message8);
		
		tvHouseRent1 = (TextView)findViewById(R.id.tv_detail_house_rent1);
		tvHouseRent2 = (TextView)findViewById(R.id.tv_detail_house_rent2);
		tvHouseRent3 = (TextView)findViewById(R.id.tv_detail_house_rent3);
		tvHouseRent4 = (TextView)findViewById(R.id.tv_detail_house_rent4);
		tvHouseRent5 = (TextView)findViewById(R.id.tv_detail_house_rent5);
		tvHouseRent6 = (TextView)findViewById(R.id.tv_detail_house_rent6);
		
		//房产分析报告
		iv_house_report = (ImageView)findViewById(R.id.iv_house_report);
		tv_house_report = (TextView)findViewById(R.id.tv_house_report);
		tv_house_report0 = (TextView)findViewById(R.id.tv_house_report0);
		iv_house_report1 = (ImageView)findViewById(R.id.iv_house_report1);
		tv_house_report1 = (TextView)findViewById(R.id.tv_house_report1);
		tv_house_report2 = (TextView)findViewById(R.id.tv_house_report2);
		rlReport = (RelativeLayout)findViewById(R.id.rl_house_report);
		rlReport1 = (RelativeLayout)findViewById(R.id.rl_house_report1);
		
		//产权所属
		ivCompany = (ImageView)findViewById(R.id.iv_company);
		tvCompany = (TextView)findViewById(R.id.tv_company);
		//物业管理方
		ivCompany1 = (ImageView)findViewById(R.id.iv_company1);
		tvCompany1 = (TextView)findViewById(R.id.tv_company1);
		
		
		
		
		sl_detail = (VerticalScrollView) findViewById(R.id.sl_detail);
		btn_back = (ImageButton) findViewById(R.id.imageButton_leftdetail);
		btn_shopcard = (ImageButton) findViewById(R.id.button_shop_card);
		btn_share = (ImageButton) findViewById(R.id.button_share_menu);
		addbtnshopcard = (Button) findViewById(R.id.bt_add_shop_card);
		ll_knowmore = (LinearLayout) findViewById(R.id.ll_know_more);
		ll_personservice = (LinearLayout) findViewById(R.id.ll_person_service);
		
		//最后三张图片点击事件
		rlPropertyDetail1 = (RelativeLayout)findViewById(R.id.rl_property_detail1);
		rlPropertyDetail2 = (RelativeLayout)findViewById(R.id.rl_property_detail2);
		rlPropertyDetail3 = (RelativeLayout)findViewById(R.id.rl_property_detail3);
		
		
		btn_back.setOnClickListener(this);
		btn_shopcard.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		addbtnshopcard.setOnClickListener(this);
		ll_knowmore.setOnClickListener(this);
		ll_personservice.setOnClickListener(this);
		rlPropertyDetail1.setOnClickListener(this);
		rlPropertyDetail2.setOnClickListener(this);
		rlPropertyDetail3.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_leftdetail://
			this.finish();
			break;
		case R.id.button_shop_card://
			Intent intent = new Intent(mContext, ShopCardActivity.class);
			startActivity(intent);
			break;
		case R.id.button_share_menu://
			SharePopupWindow addPopWindow = new SharePopupWindow(mContext);
			addPopWindow.showPopupWindow(btn_share);

			break;
		case R.id.bt_add_shop_card://
			Intent intentaddshopcard = new Intent(mContext,
					ShopCardActivity.class);
			startActivity(intentaddshopcard);
			break;

		case R.id.ll_know_more://
			Intent intentknowmore = new Intent(mContext, KnowMoreActivity.class);
			startActivity(intentknowmore);
			break;
		case R.id.ll_person_service://
			Uri uri = Uri.parse("tel:80005006");
			Intent intentcall = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(intentcall);
			break;

		case R.id.imageButton_left://
			back();
			break;
			
			
			
			
			
		case R.id.rl_property_detail1://
			back();
			break;
		case R.id.rl_property_detail2://
			back();
			break;
		case R.id.rl_property_detail3://
			back();
			break;
			
//		case R.id.rl_house_report://PDF点击事件
//			
//			break;
//		case R.id.rl_house_report1://PDF点击事件
//			
//			break;
			
			

		default:
			break;
		}
	}

	@Override
	protected void back() {
		hideInput();
		PropertyDetailsActivity.this.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}	
	
	//地图的各个生命周期
	 @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	        mMapView.onDestroy();  
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        mMapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        mMapView.onPause();  
	        }
	    
	    //获取网络数据
private void loadDataFromNet() {
	
	    	//接口 http://gotyh.com/tianyuanadmin/index.php/Home/Index/index?id=49&rid=266
			String url = LBUrls.DETAIL_PRE_URL+SPUtils.getString(this, "user_id")+"&rid="+mRid;
			// 创建okHttpClient对象
			OkHttpClient mOkHttpClient = new OkHttpClient();
			// 创建一个Request
			final Request request = new Request.Builder().url(url).build();
			// new call
			Call call = mOkHttpClient.newCall(request);
			// 请求加入调度
			call.enqueue(new Callback() {

				@Override
				public void onFailure(Request arg0, IOException arg1) {
					
					
				}

				@Override
				public void onResponse(Response response) throws IOException {
					String detailMessage = response.body().string();
					System.out.print("onResponse首页详情页面："+detailMessage);
					LBDetailAssetsModel detailAssetsModel = new Gson().fromJson(
							detailMessage, LBDetailAssetsModel.class);
					
					System.out.print("onResponse:房屋详情："+detailAssetsModel.a.get(0).strA);
					
					Message msg = mhandler.obtainMessage();
					msg.what = MAIN_DETAIL;
					msg.obj = detailAssetsModel;
					mhandler.sendMessage(msg);
					
				}

			
			});
			
		}

}
