package com.echuang.tianyuanhao.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.CommonWebActivity;
import com.echuang.tianyuanhao.adapter.DynamicpageListAdapter;
import com.echuang.tianyuanhao.adapter.DynamicpageListAdapterA;
import com.echuang.tianyuanhao.adapter.DynamicpageListAdapterB;
import com.echuang.tianyuanhao.adapter.DynamicpageListAdapterC;
import com.echuang.tianyuanhao.adapter.DynamicpageListAdapterD;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.DynamicModel;
import com.echuang.tianyuanhao.model.LBDynamic;
import com.echuang.tianyuanhao.model.LBDynamic.DynamicItem;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

/**
 * 动态列表布局
 * 
 * @ClassName: DynamicPageFragment
 * @Description: TODO
 */
@SuppressLint("ValidFragment") public class DynamicPageFragment extends BaseFragment implements OnLoadListener {
	private static final int FOOT = 0;
	private static final int HEAD = 1;
	protected static final int DYNAMIC_MESSAGE = 0;
	protected static final int DYNAMIC_MESSAGE_ONE = 1;
	protected static final int DYNAMIC_MESSAGE_TWO = 2;
	protected static final int DYNAMIC_MESSAGE_THREE = 3;
	protected static final int DYNAMIC_MESSAGE_FORE = 4;
	private int page = 1;
	private View parentView;
	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;

	private DynamicpageListAdapter mAdapter;// 适配器1
	private DynamicpageListAdapterA mAdapterA;// 适配器2
	private DynamicpageListAdapterB mAdapterB;// 适配器3
	private DynamicpageListAdapterC mAdapterC;// 适配器4
	private DynamicpageListAdapterD mAdapterD;// 适配器5

	private List<DynamicModel> datas = new ArrayList<DynamicModel>();// 数据列表
	private int action = 0;

	// 网络获取信息的处理
	public static List<DynamicItem> resultList = new ArrayList<LBDynamic.DynamicItem>();
	public static List<DynamicItem> resultListA = new ArrayList<LBDynamic.DynamicItem>();
	public static List<DynamicItem> resultListB = new ArrayList<LBDynamic.DynamicItem>();
	public static List<DynamicItem> resultListC = new ArrayList<LBDynamic.DynamicItem>();
	public static List<DynamicItem> resultListD = new ArrayList<LBDynamic.DynamicItem>();

	private static Handler mhandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DYNAMIC_MESSAGE:
				LBDynamic dynamicMessage = (LBDynamic) msg.obj;
				for (DynamicItem dynamicItem : dynamicMessage.Result) {
					resultList.add(dynamicItem);
				}
				break;
			case DYNAMIC_MESSAGE_ONE:
				LBDynamic dynamicMessageA = (LBDynamic) msg.obj;
				for (DynamicItem dynamicItem : dynamicMessageA.Result) {
					resultListA.add(dynamicItem);
				}
				break;
			case DYNAMIC_MESSAGE_TWO:
				LBDynamic dynamicMessageB = (LBDynamic) msg.obj;
				for (DynamicItem dynamicItem : dynamicMessageB.Result) {
					resultListB.add(dynamicItem);
				}
				break;
			case DYNAMIC_MESSAGE_THREE:
				LBDynamic dynamicMessageC = (LBDynamic) msg.obj;
				for (DynamicItem dynamicItem : dynamicMessageC.Result) {
					resultListC.add(dynamicItem);
				}
				break;
			case DYNAMIC_MESSAGE_FORE:
				LBDynamic dynamicMessageD = (LBDynamic) msg.obj;
				for (DynamicItem dynamicItem : dynamicMessageD.Result) {
					resultListD.add(dynamicItem);
				}
				break;

			default:
				break;
			}

		}
	};

	public DynamicPageFragment() {

	}

	public DynamicPageFragment(int i) {
		this.action = i;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fr_dynamic_page, container,
				false);
		initLoading(parentView);
		// 通过action加载不同标签下的的数据 通过action过滤数据
		loadDataByAction(action);

		initView();
		initEvents();
		return parentView;

	}

	private void loadDataByAction(int action) {
		switch (action) {
		case 0:
			loadDataFromNet(action);
			break;
		case 1:
			loadDataFromNet1(action);
			break;
		case 2:
			loadDataFromNet2(action);
			break;
		case 3:
			loadDataFromNet3(action);
			break;
		case 4:
			loadDataFromNet4(action);
			break;

		default:
			break;
		}

	}

	@Override
	protected void initView() {
		mPullToRefreshLayout = ((PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view));
		mListView = (PullableListView) parentView
				.findViewById(R.id.listView_content);

		mPullToRefreshLayout.setIsNewRefresh(true);// 设置是新的刷新样式
		mPullToRefreshLayout.setEnablePullTorefresh(false);// 取消下拉效果
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				loadData(HEAD);
			}
		});

		// mPullToRefreshLayout.setEnablePullTorefresh(false);// 禁止下拉刷新
		mListView.setOnLoadListener(this);

		// 根据action筛选
		if (action == 0) {
			mAdapter = new DynamicpageListAdapter(getActivity());
			mAdapter.setData(datas, resultList);
			mListView.setAdapter(mAdapter);
		} else if (action == 1) {
			mAdapterA = new DynamicpageListAdapterA(getActivity());
			mAdapterA.setData(datas, resultListA);
			mListView.setAdapter(mAdapterA);
		} else if (action == 2) {
			mAdapterB = new DynamicpageListAdapterB(getActivity());
			mAdapterB.setData(datas, resultListB);
			mListView.setAdapter(mAdapterB);
		} else if (action == 3) {
			mAdapterC = new DynamicpageListAdapterC(getActivity());
			mAdapterC.setData(datas, resultListC);
			mListView.setAdapter(mAdapterC);
		} else if (action == 4) {
			mAdapterD = new DynamicpageListAdapterD(getActivity());
			mAdapterD.setData(datas, resultListD);
			mListView.setAdapter(mAdapterD);
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						CommonWebActivity.class);
				intent.putExtra(StrRes.title, "标题");
				// 修改前的intent.putExtra(StrRes.url, "http://mp.weixin.qq.com/s?__biz=MzA4MTE4NjUzMg==&mid=2655437194&idx=1&sn=da7e077711986f261c4ab8985179f0bb&scene=2&srcid=0707pBCZoXKMIcLAEW1q3VXD#wechat_redirect");
				selectClickUrl(position,intent);
				startActivity(intent);
			}

			private void selectClickUrl(int position,Intent intent) {

						switch (action) {
						case 0:
							intent.putExtra(StrRes.url, resultList.get(position).d_text).putExtra(StrRes.title, "");
							break;
						case 1:
							intent.putExtra(StrRes.url, resultListA.get(position).d_text).putExtra(StrRes.title, "");
							break;
						case 2:
							intent.putExtra(StrRes.url, resultListB.get(position).d_text).putExtra(StrRes.title, "");
							break;
						case 3:
							intent.putExtra(StrRes.url, resultListC.get(position).d_text).putExtra(StrRes.title, "");
							break;
						case 4:
							intent.putExtra(StrRes.url, resultListD.get(position).d_text).putExtra(StrRes.title, "");
							break;
						
						default:
							break;
						}
				
			}
		});
		mListView.setIscloseload(true);
		// mPullToRefreshLayout.startRefresh();
		loadData(HEAD);
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
						datas = DecodeJsonManage.getDynamicModelList(content,
								datas);

						if (action == 0) {
							mAdapter.notifyDataSetChanged();
						} else if (action == 1) {
							mAdapterA.notifyDataSetChanged();
						} else if (action == 2) {
							mAdapterB.notifyDataSetChanged();
						} else if (action == 3) {
							mAdapterC.notifyDataSetChanged();
						} else if (action == 4) {
							mAdapterD.notifyDataSetChanged();
						}

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
		if (action == 0) {
			mAdapter.notifyDataSetChanged();
		} else if (action == 1) {
			mAdapterA.notifyDataSetChanged();
		} else if (action == 2) {
			mAdapterB.notifyDataSetChanged();
		} else if (action == 3) {
			mAdapterC.notifyDataSetChanged();
		} else if (action == 4) {
			mAdapterD.notifyDataSetChanged();
		}

	}

	protected void initEvents() {
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

	public void loadDataFromNet(final int action) {

		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				LBUrls.DYNAMIC_PRE_URL + action).build();
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
				String dynamicMessage = response.body().string();
				final LBDynamic dynamicModel = new Gson().fromJson(
						dynamicMessage, LBDynamic.class);
				System.out.println("------------分割线-------------");
				// action = 0,1,2,3,4 1和2没有数据 以后加
				Message msg = mhandler.obtainMessage();
				msg.what = DYNAMIC_MESSAGE;
				msg.obj = dynamicModel;
				mhandler.sendMessage(msg);

			}
		});

	}

	private void loadDataFromNet1(int action1) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				LBUrls.DYNAMIC_PRE_URL + action1).build();
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
				String dynamicMessageA = response.body().string();
				final LBDynamic dynamicModelA = new Gson().fromJson(
						dynamicMessageA, LBDynamic.class);
				System.out.println("------------分割线-------------");
				// action = 0,1,2,3,4 1和2没有数据 以后加
				Message msg = mhandler.obtainMessage();
				msg.what = DYNAMIC_MESSAGE_ONE;
				msg.obj = dynamicModelA;
				mhandler.sendMessage(msg);

			}
		});

	}

	private void loadDataFromNet2(int action2) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				LBUrls.DYNAMIC_PRE_URL + action2).build();
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
				String dynamicMessageB = response.body().string();
				final LBDynamic dynamicModelB = new Gson().fromJson(
						dynamicMessageB, LBDynamic.class);
				System.out.println("------------分割线-------------");
				// action = 0,1,2,3,4 1和2没有数据 以后加
				Message msg = mhandler.obtainMessage();
				msg.what = DYNAMIC_MESSAGE_TWO;
				msg.obj = dynamicModelB;
				mhandler.sendMessage(msg);
			}
		});

	}

	private void loadDataFromNet3(int action3) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				LBUrls.DYNAMIC_PRE_URL + action3).build();
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
				String dynamicMessageC = response.body().string();
				final LBDynamic dynamicModelC = new Gson().fromJson(
						dynamicMessageC, LBDynamic.class);
				System.out.println("------------分割线-------------");
				// action = 0,1,2,3,4 1和2没有数据 以后加
				Message msg = mhandler.obtainMessage();
				msg.what = DYNAMIC_MESSAGE_THREE;
				msg.obj = dynamicModelC;
				mhandler.sendMessage(msg);

			}
		});

	}

	private void loadDataFromNet4(int action4) {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().url(
				LBUrls.DYNAMIC_PRE_URL + action4).build();
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
				String dynamicMessageD = response.body().string();
				final LBDynamic dynamicModelD = new Gson().fromJson(
						dynamicMessageD, LBDynamic.class);
				System.out.println("------------分割线-------------");
				// action = 0,1,2,3,4 1和2没有数据 以后加
				Message msg = mhandler.obtainMessage();
				msg.what = DYNAMIC_MESSAGE_FORE;
				msg.obj = dynamicModelD;
				mhandler.sendMessage(msg);

			}
		});

	}
}
