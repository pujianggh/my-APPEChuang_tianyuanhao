package com.echuang.tianyuanhao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.MainAssetsListAdapter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.MainAssetsModel;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.loopj.android.http.RequestParams;

/**
 * 查询结果
 * 
 * @ClassName: SearchResultActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-19 下午4:00:43
 */
public class SearchResultActivity extends BaseSwipeBackActivity implements
		OnClickListener, OnLoadListener {
	private static final int FOOT = 0;
	private static final int HEAD = 1;
	private int page = 1;
	private PullToRefreshLayout mPullToRefreshLayout;
	private PullableListView mListView;

	private MainAssetsListAdapter mAdapter;// 适配器
	private List<MainAssetsModel> datas = new ArrayList<MainAssetsModel>();// 数据列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_search_result);
		initView();
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

	/**
	 * 组件初始化
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.echuang.tianyuanhao.swipebackbase.SwipeBackActivity#initView()
	 */
	@Override
	protected void initView() {
		initTitle(getResources().getString(R.string.t_search_result), "", 0);
		mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		mListView = (PullableListView) findViewById(R.id.listView_content);
		mPullToRefreshLayout.setIsNewRefresh(true);// 设置是新的刷新样式
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				loadData(HEAD);
			}
		});
		mListView.setOnLoadListener(this);
		mAdapter = new MainAssetsListAdapter(this);
		mAdapter.setData(datas);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		mListView.setIscloseload(true);
		loadData(HEAD);
	}

	/**
	 * 数据获取
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
		NetUtils.getInstance().post(this, url, params, new HttpCallback() {

			@Override
			public void onSuccess(String content) {
				hideLoadingView();
				stopDialog();
				// 通知加载完毕
				mListView.finishLoading();
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				if (refreshtype == HEAD) {
					datas.clear();
				}
				datas = DecodeJsonManage.getMainAssetsList(content, datas);
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
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
