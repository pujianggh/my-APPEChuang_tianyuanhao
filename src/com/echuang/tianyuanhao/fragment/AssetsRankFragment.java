package com.echuang.tianyuanhao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.AssetsRankListAdapter;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.base.BaseWebFragment;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.EarningModel;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.loopj.android.http.RequestParams;

/**
 * 资产排名
 * 
 * @ClassName: AssetsRankFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-14 上午9:17:56
 */
public class AssetsRankFragment extends BaseFragment implements
		OnClickListener, OnLoadListener {
	public static BaseWebFragment instance = null;
	private static final int FOOT = 0;
	private static final int HEAD = 1;
	private int page = 1;
	private View parentView;
	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;

	private AssetsRankListAdapter mAdapter;// 适配器
	private List<EarningModel> datas = new ArrayList<EarningModel>();// 数据列表
	private String mStatus = "";// 保存类型状态

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			mStatus = bundle.getString(StrRes.status);
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.fr_assets_rank, container, false);
		initLoading(parentView);
		initView();
		return parentView;
	}

	@Override
	protected void initView() {
		mPullToRefreshLayout = ((PullToRefreshLayout) parentView
				.findViewById(R.id.refresh_view));
		mListView = (PullableListView) parentView
				.findViewById(R.id.listView_content);
		mPullToRefreshLayout.setIsNewRefresh(true);// 设置是新的刷新样式
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				loadData(HEAD);
			}
		});
		mPullToRefreshLayout.setEnablePullTorefresh(false);// 禁止自动下拉刷新
		mListView.setOnLoadListener(this);
		mAdapter = new AssetsRankListAdapter(getActivity(), mStatus);
		mAdapter.setData(datas);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		mListView.setIscloseload(true);
		//mPullToRefreshLayout.startRefresh();//设置自动刷新
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
						datas = DecodeJsonManage.getNewsList(content, datas);
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

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	protected void back() {
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
}
