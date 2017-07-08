package com.echuang.tianyuanhao.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.LinkActivity;
import com.echuang.tianyuanhao.activity.PropertyDetailsActivity;
import com.echuang.tianyuanhao.activity.SearchActivity;
import com.echuang.tianyuanhao.adapter.MainAssetsListAdapter;
import com.echuang.tianyuanhao.adapter.MainAssetsListAdapter.OnItemShowPopupClick;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.dialog.ShowCentorWindow;
import com.echuang.tianyuanhao.dialog.ShowTextWindow;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.ADInfo;
import com.echuang.tianyuanhao.model.CornerModel;
import com.echuang.tianyuanhao.model.LBMainAssetsModel;
import com.echuang.tianyuanhao.model.LBMainAssetsModel.Resultdata;
import com.echuang.tianyuanhao.model.LBRecycleViewPagerImg;
import com.echuang.tianyuanhao.model.LBRecycleViewPagerImg.RecycleImgUrl;
import com.echuang.tianyuanhao.model.MainAssetsModel;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.echuang.tianyuanhao.view.RangeSeekBar;
import com.echuang.tianyuanhao.view.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.echuang.tianyuanhao.view.pager.CycleViewPager;
import com.echuang.tianyuanhao.view.pager.CycleViewPager.ImageCycleViewListener;
import com.echuang.tianyuanhao.view.pager.ViewFactory;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

/**
 * 主界面
 * 
 * @ClassName: MainHomeFragment
 * @Description: TODO
 * @author Libao
 */
public class MainHomeFragment extends BaseFragment implements OnClickListener,
		OnLoadListener,OnItemShowPopupClick {
	private static final int FOOT = 0;
	private static final int HEAD = 1;
	private static final int NET_DATA_FLAG = 3;
	private static final int RECYCLE_IMG_DATA = 4;
	protected static final int RECYCLEPAGER_TO_DETAILACTIVITY = 5;

	private int page = 1;
	private View parentView;
	private ImageView igv_right;
	private TextView textView_title;
	private CycleViewPager mCycleViewPager;// 标底循环滚动
	private RelativeLayout mRlyViewpager;// 头部循环滚动布局
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private List<ADInfo> adInfos = new ArrayList<ADInfo>();
	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;
	// 右上角弹出框
	private ShowCentorWindow mShowTopCentorWindow1;
	private ShowCentorWindow mShowTopCentorWindow2;
	private ShowCentorWindow mShowTopCentorWindow3;
	private ShowTextWindow mShowTextWindow;//数据层展示
	private List<CornerModel> top_list1 = new ArrayList<CornerModel>();
	private List<CornerModel> top_list2 = new ArrayList<CornerModel>();
	private List<CornerModel> top_list3 = new ArrayList<CornerModel>();
	private MainAssetsListAdapter mAdapter;
	// 数据列表
	private List<MainAssetsModel> datas = new ArrayList<MainAssetsModel>();
	private LinearLayout lly_select1;
	private LinearLayout lly_select2;
	private LinearLayout lly_select3;
	private TextView tv_select1;
	private TextView tv_select2;
	private TextView tv_select3;
	private SeekBar mSeekBar;

	// 获取网络数据
	public static String[] webUrls = new String[3];
	private static String[] strImg = new String[3];
	private static String[] imageUrls = new String[3];
	private static String[] recycleRids = new String[3];
	private static List<Resultdata> mList = new ArrayList<Resultdata>();
	public static List<RecycleImgUrl> mRecycleImgList = new ArrayList<RecycleImgUrl>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.fr_main_home, container, false);
		initView();
		imageUrls[0] = "http://tianyuanhao.oss-cn-shanghai.aliyuncs.com/img/2016-07-22/banner4.jpg";
		imageUrls[1] = "http://tianyuanhao.oss-cn-shanghai.aliyuncs.com/img/2016-07-22/banner2.jpg";
		imageUrls[2] = "http://tianyuanhao.oss-cn-shanghai.aliyuncs.com/img/2016-07-22/banner3.jpg";

		// imageUrls[3] =
		// "http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg";
		// 加载轮播图图片
		loadViewPagerData();
		loadDataFromNet();
		loadData(HEAD);
		initBinnerData();
		initEvents();
		initTopCorner();

		return parentView;
	}

	@Override
	protected void initView() {

		igv_right = (ImageView) parentView.findViewById(R.id.igv_right);
		textView_title = (TextView) parentView
				.findViewById(R.id.textView_title);
		mPullToRefreshLayout = ((PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view));
		mListView = (PullableListView) parentView
				.findViewById(R.id.listView_content);

		/** 添加头部 **/
		View headView = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_head, null);
		mCycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_viewpager);
		mRlyViewpager = (RelativeLayout) headView
				.findViewById(R.id.rly_viewpager);
		lly_select1 = (LinearLayout) headView.findViewById(R.id.lly_select1);
		lly_select2 = (LinearLayout) headView.findViewById(R.id.lly_select2);
		lly_select3 = (LinearLayout) headView.findViewById(R.id.lly_select3);
		tv_select1 = (TextView) headView.findViewById(R.id.tv_select1);
		tv_select2 = (TextView) headView.findViewById(R.id.tv_select2);
		tv_select3 = (TextView) headView.findViewById(R.id.tv_select3);
		mSeekBar = (SeekBar) headView.findViewById(R.id.sb_price_area);
		RangeSeekBar<Integer> mSeekBar = new RangeSeekBar<Integer>(0, 100,
				getActivity());
		// seekBar.setNotifyWhileDragging(true);
		// mSeekBar.setUnit("$");
		// mSeekBar.setUnitStr("元");
		mSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				// 价格区间值
			}
		});
		ViewGroup layout = (ViewGroup) headView
				.findViewById(R.id.lly_price_area);
		layout.addView(mSeekBar);
		mListView.addHeaderView(headView);
		/******* 根据手机屏幕的宽度，动态设置滚动标底的高度 ********/
		/** 添加尾部 **/
		View footView = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_foot, null);
		mListView.addFooterView(footView);
		// android.view.ViewGroup.LayoutParams pp
		// =mRlyViewpager.getLayoutParams();
		// mRlyViewpager.getLayoutParams();
		// 更加规定宽高图片计算组件高度，并设置
		// pp.height =DataTools.getWindowWidth(getActivity())*578/1242;
		// mRlyViewpager.setLayoutParams(pp);

		mPullToRefreshLayout.setIsNewRefresh(true);// 设置是新的刷新样式
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				loadData(HEAD);
			}
		});
		// mListView.setOnLoadListener(this);

		mAdapter = new MainAssetsListAdapter(getActivity(), mList);
		mAdapter.setData(datas);
		mAdapter.setItemShowPopupClick(this);
		mListView.setAdapter(mAdapter);
		mListView.setIscloseload(true);
		// mPullToRefreshLayout.startRefresh();
		loadData(HEAD);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lly_select1:// 选择1
			mShowTopCentorWindow1.showAsDropDown(lly_select1);
			break;
		case R.id.lly_select2:// 选择2
			mShowTopCentorWindow2.showAsDropDown(lly_select2);
			break;
		case R.id.lly_select3:// 选择3
			mShowTopCentorWindow3.showAsDropDown(lly_select3);
			break;
		case R.id.igv_right:
		case R.id.textView_title:// 查询按钮
			startActivity(new Intent(getActivity(), SearchActivity.class));
			break;
		default:
			break;
		}
	}

	/**
	 * 请求数据
	 * 
	 * @Title: loadData
	 * @Description: TODO
	 * @param @param refreshtype
	 * @return void
	 * @throws
	 */
	private void loadData(final int refreshtype) {
		if (refreshtype == HEAD) {
			page = 1;
		}
		String url = UrlManage.BASE_URL + UrlManage.EARNING_LIST;
		RequestParams params = new RequestParams();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(StrRes.categoryId, "353695c6-f37f-4555-8e30-4b456abb17d0");
		map.put(StrRes.page, "" + page);
		params.put(StrRes.message, Utils.addParams(map));
		NetUtils.getInstance().post(getActivity(), url, params,
				new HttpCallback() {
					@Override
					public void onSuccess(String content) {
						hideLoadingView();
						stopDialog();
						// 通知加载完毕
						mListView.finishLoading();
						mPullToRefreshLayout
								.refreshFinish(PullToRefreshLayout.SUCCEED);
						if (refreshtype == HEAD) {
							datas.clear();
						}
						datas = DecodeJsonManage.getMainAssetsList(content,
								datas);
						mAdapter.notifyDataSetChanged();
						if (DecodeJsonManage.isHasNextPage(content)) {
							mListView.setIscloseload(false);
						} else {
							mListView.setIscloseload(true);
						}
						page++;
					}

					@Override
					public void onFailure(String content, int statusCode) {
						stopDialog();
						mListView.finishLoading();// 通知加载完毕
						mPullToRefreshLayout
								.refreshFinish(PullToRefreshLayout.FAIL);
						toast(R.string.linked_server_error);
						if (datas != null && datas.size() == 0) {
							showLoadingView(false);
						} else {
							hideLoadingView();
						}
					}
				});
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 标第赋值
	 * 
	 * @Title: initBinnerData
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */

	private void initBinnerData() {
		imageViews.clear();

		// 这里可以通过imageUrls的长度 动态控制轮播图的页面数量
		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i);
			adInfos.add(info);
		}
		// 将最后一个ImageView添加进来
		int listSize = adInfos.size();
		if (listSize > 0) {
			imageViews.add(ViewFactory.getImageView(getActivity(),
					adInfos.get(listSize - 1).getUrl()));
			for (int i = 0; i < listSize; i++) {
				imageViews.add(ViewFactory.getImageView(getActivity(), adInfos
						.get(i).getUrl()));
			}
			// 将第一个ImageView添加进来
			imageViews.add(ViewFactory.getImageView(getActivity(),
					adInfos.get(0).getUrl()));
			// 设置循环，在调用setData方法前调用
			mCycleViewPager.setCycle(true);

			// 在加载数据前设置是否循环
			mCycleViewPager.setData(imageViews, adInfos,
					new ImageCycleViewListener() {

						/**
						 * @Description: TODO
						 * @Description: 轮播图点击事件
						 * 
						 */
						@Override
						public void onImageClick(ADInfo info, int postion,
								View imageView) {
							if (mCycleViewPager.isCycle()
									&& adInfos.size() > (postion - 1)
									&& adInfos.get(postion - 1) != null) {
								/**
								 * @Description: TODO
								 * @Description:轮播图点击请求
								 */
								// 向服务器发送一个请求
								lbSendRequest(postion);

							}
						}
					});
			// 设置轮播
			mCycleViewPager.setWheel(true);
			// 设置轮播时间，默认5000ms
			mCycleViewPager.setTime(5000);
			// 设置圆点指示图标组居中显示，默认靠右
			mCycleViewPager.setIndicatorCenter();

		}
	}

	protected void initEvents() {
		igv_right.setOnClickListener(this);
		textView_title.setOnClickListener(this);
		lly_select1.setOnClickListener(this);
		lly_select2.setOnClickListener(this);
		lly_select3.setOnClickListener(this);
	}

	/**
	 * 设置选择数据
	 * 
	 * @Title: initTopCorner
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initTopCorner() {
		top_list1.clear();
		top_list2.clear();
		top_list3.clear();
		top_list1.add(new CornerModel("全部", "", 0));
		top_list1.add(new CornerModel("美国", "usa", 0));
		top_list1.add(new CornerModel("英国", "en", 0));
		top_list1.add(new CornerModel("澳大利亚", "ao", 0));
		top_list1.add(new CornerModel("日本", "jp", 0));
		mShowTopCentorWindow1 = new ShowCentorWindow(getActivity(), top_list1,
				new ShowCentorWindow.OnItemSelectedListener() {
					@Override
					public void OnSelected(int position, CornerModel data) {
						tv_select1
								.setText(top_list1.get(position).getContent());
					}
				});
		mShowTopCentorWindow1.setAdapterSelected(true);
		mShowTopCentorWindow1.setAdapterNeedArrow(true);

		top_list2.add(new CornerModel("全部", "", 0));
		top_list2.add(new CornerModel("由低到高", "1", 0));
		top_list2.add(new CornerModel("由高到低", "2", 0));
		mShowTopCentorWindow2 = new ShowCentorWindow(getActivity(), top_list2,
				new ShowCentorWindow.OnItemSelectedListener() {

					@Override
					public void OnSelected(int position, CornerModel data) {
						tv_select2
								.setText(top_list2.get(position).getContent());
					}
				});
		mShowTopCentorWindow2.setAdapterSelected(true);
		mShowTopCentorWindow2.setAdapterNeedArrow(true);

		top_list3.add(new CornerModel("全部", "", 0));
		top_list3.add(new CornerModel("由低到高", "1", 0));
		top_list3.add(new CornerModel("由高到低", "2", 0));
		mShowTopCentorWindow3 = new ShowCentorWindow(getActivity(), top_list3,
				new ShowCentorWindow.OnItemSelectedListener() {

					@Override
					public void OnSelected(int position, CornerModel data) {
						tv_select3
								.setText(top_list3.get(position).getContent());
					}
				});
		mShowTopCentorWindow3.setAdapterSelected(true);
		mShowTopCentorWindow3.setAdapterNeedArrow(true);
	}

	public interface OnTypeSelectListener {
		void onTypeSelect(int position, String value);
	}

	@Override
	protected void back() {
		hideInput();
	}

	@Override
	protected void next() {

	}

	@Override
	public void onLoad(PullableListView pullableListView) {
		loadData(FOOT);
	}

	@Override
	protected void Retry() {
		super.Retry();
		startDialog();
		loadData(HEAD);
	}

	// ---------------------------获取首页轮播图信息------------------------------
	private void loadViewPagerData() {

		OkHttpUtils.get(LBUrls.recycleUrl) // 请求方式和请求url
				.tag(this)
				// 请求的tag, 主要用于取消对应的请求
				.cacheKey("cacheKey") //
				// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
				.cacheMode(CacheMode.DEFAULT) //
				// 缓存模式，详细请看缓存介绍
				.execute(new StringCallback() {

					@Override
					public void onError(Call call, Response response,
							Exception e) {
						// TODO Auto-generated method stub
						super.onError(call, response, e);
					}

					@Override
					public void onSuccess(String s, Call call, Response response) {
						// s 即为所需要的结果(这里为json字符串)
						LBRecycleViewPagerImg recycleImg = new Gson().fromJson(
								s, LBRecycleViewPagerImg.class);

						Message msg = mhandler.obtainMessage();
						msg.what = RECYCLE_IMG_DATA;
						msg.obj = recycleImg;
						mhandler.dispatchMessage(msg);
						// sendMessage(msg);

					}
				});

	}

	// ---------------------------获取网络数据------------------------------
	private void loadDataFromNet() {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				"http://gotyh.com/tianyuanadmin/index.php/Home/Index/doSearch")
				.build();
		// new call
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(new Callback() {

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}

			@Override
			public void onResponse(Call arg0, Response response)
					throws IOException {
				String roomMessage = response.body().string();
				final LBMainAssetsModel goodonedata1 = new Gson().fromJson(
						roomMessage, LBMainAssetsModel.class);
				Message msg = mhandler.obtainMessage();
				msg.what = NET_DATA_FLAG;
				msg.obj = goodonedata1;
				mhandler.sendMessage(msg);

			}
		});

	}

	private void lbSendRequest(int postion) {

		// 这里的postion是从1开始的
		int ridState = 4;
		if (recycleRids[postion - 1] != null) {
			ridState = Integer.parseInt(recycleRids[postion - 1]);
		}
		switch (ridState) {
		case 1:
			// 跳转到某个url web界面
			toast("跳转web界面");
			Intent linkIntent = new Intent(getActivity(), LinkActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("webUrl", webUrls[postion - 1]);// user
			linkIntent.putExtras(bundle);
			startActivity(linkIntent);
			break;
		case 2:
			// 跳转到关于界面 //包没有用v4兼容包
			toast("跳转关于界面");
			break;
		case 3:
			// 跳转到详情页面 webUrls[2] 这里的url表示rid 把轮播图的rid传到详情页

			toast("跳转详情界面");
			Intent detailIntent = new Intent(getActivity(),
					PropertyDetailsActivity.class);
			Bundle detailBundle = new Bundle();
			detailBundle.putString("rid", webUrls[2]);
			System.out.print("跳转详情界面====:" + webUrls[2]);
			detailIntent.putExtras(detailBundle);
			startActivity(detailIntent);

			break;

		default:
			break;
		}

	}

	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			case NET_DATA_FLAG:
				LBMainAssetsModel assetsNetData = (LBMainAssetsModel) msg.obj;
				if (assetsNetData != null && assetsNetData.Result != null) {
					for (int i = 0; i < assetsNetData.Result.size(); i++) {
						mList.add(assetsNetData.Result.get(i));
					}
				}
				break;

			case RECYCLE_IMG_DATA:
				LBRecycleViewPagerImg recycleimgUrl = (LBRecycleViewPagerImg) msg.obj;
				if (recycleimgUrl != null && recycleimgUrl.Result != null) {
					for (int i = 0; i < recycleimgUrl.Result.size(); i++) {
						strImg[i] = recycleimgUrl.Result.get(i).img;
						webUrls[i] = recycleimgUrl.Result.get(i).url;
						imageUrls[i] = LBInstance.HOME_IMG_URL + strImg[i];
						recycleRids[i] = recycleimgUrl.Result.get(i).c_states;
						System.out.println("图片的Rid:"
								+ recycleimgUrl.Result.get(i).c_states);
						System.out.println("轮播图地址==============："
								+ LBInstance.HOME_IMG_URL + strImg[i]);
					}
				}
				break;
			case RECYCLEPAGER_TO_DETAILACTIVITY:
				// String s = (String) msg.obj;
				// Intent detailIntent = new Intent(getActivity(),
				// PropertyDetailsActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("s", s);// user
				// detailIntent.putExtras(bundle);
				// startActivity(detailIntent);
				break;

			}
		}
	};

	@Override
	public void itemShowPopupClick(View parent, String infoString) {
		if (mShowTextWindow == null) {
			mShowTextWindow = new ShowTextWindow(getActivity(), infoString);
		} else {   
			mShowTextWindow.setInfoString(infoString);
		}
		if (mShowTextWindow.isShowing()) {
			mShowTextWindow.dismiss();
		} 
		int[] location = new int[2];  
		parent.getLocationOnScreen(location);  
		mShowTextWindow.showAtLocation(parent, Gravity.NO_GRAVITY,location[0], location[1]-parent.getHeight()-80);
	}
}
