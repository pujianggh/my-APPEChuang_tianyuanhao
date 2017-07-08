package com.echuang.tianyuanhao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.AddYouHouseActivity;
import com.echuang.tianyuanhao.activity.EarningManageActivity;
import com.echuang.tianyuanhao.activity.MainActivity;
import com.echuang.tianyuanhao.adapter.EarningListAdapter;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.dialog.CommonSelectWindow;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.CornerModel;
import com.echuang.tianyuanhao.model.EarningModel;
import com.echuang.tianyuanhao.model.LBEarningModel;
import com.echuang.tianyuanhao.model.LBEarningModel.EarningDetail;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.echuang.tianyuanhao.view.LiBaoBrokenLineView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

/**
 * 收益主界面
 * 
 * @ClassName: EarningFragment
 * @Description: TODO
 * @author libao
 * @date 2016-9-7 下午18:53:24
 */
public class EarningFragment extends BaseFragment implements OnLoadListener,
		OnClickListener {
	private static final int FOOT = 0;
	private static final int HEAD = 1;
	protected static final int EARNING_NET_DATA_FLAG = 2;
	private int page = 1;
	private View parentView;
	private ImageView mIgvAdd;
	private Button mTab1, mTab2;
	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;

	private EarningListAdapter mAdapter;// 适配器
	private List<EarningModel> datas = new ArrayList<EarningModel>();// 数据列表
	// 弹出选择框
	private CommonSelectWindow mCommonSelectWindow;
	private List<CornerModel> corner_list = new ArrayList<CornerModel>();
	private ImageView ivHome;
	private TextView tvLb1;
	private TextView tvLb2;
	private TextView tvLb3;
	private TextView tvLb4;
	private LiBaoBrokenLineView lbLineView;

	// 网络相关的数据
	public List<EarningDetail> earningDetailLists = new ArrayList<LBEarningModel.EarningDetail>();
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EARNING_NET_DATA_FLAG:

				LBEarningModel earningModel = (LBEarningModel) msg.obj;
				tvLb2.setText(earningModel.estate.all);
				tvLb4.setText(earningModel.estate.rent);

				if (earningModel != null) {
					for (int i = 0; i < earningModel.Result.size(); i++) {
						earningDetailLists.add(earningModel.Result.get(i));
					}
				}

				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fr_earning, container, false);
		initLoading(parentView);
		loadDataFromNet();
		initView();
		initEvents();
		return parentView;
	}

	@Override
	protected void initView() {

		mPullToRefreshLayout = ((PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view));
		mListView = (PullableListView) parentView
				.findViewById(R.id.listView_content);
		/** 添加头部 **/
		View headView = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_earning_head, null);
		// 头部切换资产收入内容显示
		lbLineView = (LiBaoBrokenLineView) headView
				.findViewById(R.id.lb_broken_line);
		ivHome = (ImageView) headView.findViewById(R.id.igv_add);

		tvLb1 = (TextView) headView.findViewById(R.id.lb_tv1);
		tvLb2 = (TextView) headView.findViewById(R.id.lb_tv2);
		tvLb3 = (TextView) headView.findViewById(R.id.lb_tv3);
		tvLb4 = (TextView) headView.findViewById(R.id.lb_tv4);

		mTab1 = (Button) headView.findViewById(R.id.btn_tab1);
		mTab2 = (Button) headView.findViewById(R.id.btn_tab2);
		mIgvAdd = (ImageView) headView.findViewById(R.id.igv_add);
		setCurrentTab(0);
		mListView.addHeaderView(headView);

		mPullToRefreshLayout.setIsNewRefresh(true);// 设置是新的刷新样式
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				loadData(HEAD);
			}
		});
		mListView.setIscloseload(true);// 关闭上拉操作
		// mPullToRefreshLayout.setEnablePullTorefresh(false);// 禁止下拉刷新
		mListView.setOnLoadListener(this);
		mAdapter = new EarningListAdapter(getActivity(), earningDetailLists);
		mAdapter.setData(datas);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 1) {
					Intent intent = new Intent(getActivity(),
							EarningManageActivity.class);
					intent.putExtra(StrRes.title, "9309 SW 21st Street");
					startActivity(intent);
				}
				if (position == 2) {
					showSelectWindow();
				}
			}
		});
		mListView.setIscloseload(true);// 关闭上拉操作
		// mPullToRefreshLayout.startRefresh();
		loadData(HEAD);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tab1:
			setCurrentTab(0);
			ivHome.setVisibility(View.VISIBLE);
			tvLb1.setVisibility(View.VISIBLE);
			tvLb2.setVisibility(View.VISIBLE);
			tvLb3.setVisibility(View.VISIBLE);
			tvLb4.setVisibility(View.VISIBLE);
			lbLineView.setVisibility(View.GONE);
			break;
		case R.id.btn_tab2:
			// 点击收入的时候
			setCurrentTab(1);
			ivHome.setVisibility(View.GONE);
			tvLb1.setVisibility(View.GONE);
			tvLb2.setVisibility(View.GONE);
			tvLb3.setVisibility(View.GONE);
			tvLb4.setVisibility(View.GONE);
			lbLineView.setVisibility(View.VISIBLE);

			break;
		case R.id.igv_add://
			showSelectWindow();
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
						datas.clear();
						EarningModel mEarningModel = new EarningModel();
						mEarningModel.setId("0");
						datas.add(mEarningModel);
						datas.add(mEarningModel);
						mPullToRefreshLayout
								.refreshFinish(PullToRefreshLayout.FAIL);
						mAdapter.notifyDataSetChanged();
						mListView.setIscloseload(true);
					}

					@Override
					public void onFailure(String content, int statusCode) {
						stopDialog();
						mListView.finishLoading();// 通知加载完毕
						mListView.setIscloseload(true);
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
	 * 设置标签
	 * 
	 * @Title: setCurrentPage
	 * @Description: TODO
	 * @param @param current
	 * @return void
	 * @throws
	 */
	private void setCurrentTab(int current) {
		mTab1.setSelected(false);
		mTab2.setSelected(false);
		if (current == 0) {
			mTab1.setSelected(true);
		} else if (current == 1) {
			mTab2.setSelected(true);
		}
	}

	protected void initEvents() {
		mTab1.setOnClickListener(this);
		mTab2.setOnClickListener(this);
		mIgvAdd.setOnClickListener(this);
	}

	/**
	 * 展示选择窗口
	 * 
	 * @Title: showSelectWindow
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void showSelectWindow() {
		if (mCommonSelectWindow == null) {
			mCommonSelectWindow = new CommonSelectWindow(getActivity());
			mCommonSelectWindow
					.setItemSelectedListener(new CommonSelectWindow.OnItemSelectedListener() {

						@Override
						public void OnSelected(int position, CornerModel data) {
							if (position == 0) {
								getActivity()
										.startActivity(
												new Intent(getActivity(),
														MainActivity.class)
														.addFlags(
																Intent.FLAG_ACTIVITY_NEW_TASK)
														.addFlags(
																Intent.FLAG_ACTIVITY_NEW_TASK));
								broadcastManager.sendBroadcast(new Intent(
										MainActivity.RECEIVER_ISFORCHECKTAB)
										.putExtra(StrRes.type, 1));
							} else {
								Intent intent = new Intent(getActivity(),
										AddYouHouseActivity.class);
								startActivity(intent);
							}
						}
					});
			mCommonSelectWindow.show();
		} else {
			mCommonSelectWindow.show();
		}
	}

	@Override
	protected void back() {
		hideInput();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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

	// 网络获取收益页面的数据
	private void loadDataFromNet() {
		OkHttpUtils.get(LBUrls.EARNING_URL) // 请求方式和请求url
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
						super.onError(call, response, e);
					}

					@Override
					public void onSuccess(String s, Call call, Response response) {
						// s 即为所需要的结果(这里为json字符串)
						LBEarningModel earningModel = new Gson().fromJson(s,
								LBEarningModel.class);

						System.out.println("收益页面的信息============>>>"
								+ earningModel.Result.get(0).room_address);
						Message msg = mhandler.obtainMessage();
						msg.what = EARNING_NET_DATA_FLAG;
						msg.obj = earningModel;
						mhandler.sendMessage(msg);

					}
				});

	}
}
