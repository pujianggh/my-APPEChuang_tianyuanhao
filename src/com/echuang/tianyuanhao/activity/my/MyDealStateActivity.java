package com.echuang.tianyuanhao.activity.my;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.PropertyDetailsActivity;
import com.echuang.tianyuanhao.adapter.LBDetailAdapter;
import com.echuang.tianyuanhao.adapter.MainAssetsListAdapter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.data.DecodeJsonManage;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout;
import com.echuang.tianyuanhao.listview.PullToRefreshLayout.OnRefreshListener;
import com.echuang.tianyuanhao.listview.PullableListView;
import com.echuang.tianyuanhao.listview.PullableListView.OnLoadListener;
import com.echuang.tianyuanhao.model.LBDealStateModel;
import com.echuang.tianyuanhao.model.LBDealStateModel.HouseMessage;
import com.echuang.tianyuanhao.model.LBMainAssetsModel;
import com.echuang.tianyuanhao.model.LBRecycleViewPagerImg;
import com.echuang.tianyuanhao.model.MainAssetsModel;
import com.echuang.tianyuanhao.netutils.NetUtils;
import com.echuang.tianyuanhao.netutils.NetUtils.HttpCallback;
import com.echuang.tianyuanhao.netutils.UrlManage;
import com.echuang.tianyuanhao.utils.SPUtils;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

/**
 * 我的交易状态
 * 
 * @ClassName: MyDealStateActivity
 * @Description: TODO
 */
public class MyDealStateActivity extends Activity implements
		OnClickListener{
	private static final String TAG = "MyDealStateActivity";
	protected static final int DEAL_STATE = 0;
	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;

	private LBDetailAdapter mAdapter;// 适配器
	private List<MainAssetsModel> datas = new ArrayList<MainAssetsModel>();// 数据列表
	
	//网络 获取数据处理
	public List<String> asstesTempoLists ;
	public List<String> imgUrls ;
	
	
	private Handler mhandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DEAL_STATE:
				
				List<String> houseMessages = new ArrayList<String>();
				final LBDealStateModel dealStateModel = (LBDealStateModel) msg.obj;
				//根据id和rid请求数据  如果存在  跳转详情页面显示
				//http://gotyh.com/tianyuanadmin/index.php/Home/Index/index?id=49&rid=266
				//"http://gotyh.com/tianyuanadmin/index.php/Home/Index/index?id="+SPUtils.getString(MyDealStateActivity.this,"user_id")+"&rid="+dealStateModel.Result.get(0).rid;
				
				//根据assets_tempo判断交易状态
				if (dealStateModel.Result!=null) {
					Toast.makeText(MyDealStateActivity.this, "交易状态请求成功"+dealStateModel.Result.get(0).room_img, Toast.LENGTH_SHORT).show();
					for (int i = 0; i < dealStateModel.Result.size(); i++) {
						houseMessages.add(dealStateModel.Result.get(i).assets_tempo);
						asstesTempoLists = new ArrayList<String>();
						imgUrls = new ArrayList<String>();
						asstesTempoLists.add(dealStateModel.Result.get(i).assets_tempo);
						imgUrls.add(dealStateModel.Result.get(i).room_img);
						
					}
				}
				
				//---------------------------------分割线-----------------------------
				
				mAdapter = new LBDetailAdapter(MyDealStateActivity.this,asstesTempoLists,imgUrls);
				mListView.setAdapter(mAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (!dealStateModel.Result.get(position).rid.isEmpty()) {
							Toast.makeText(MyDealStateActivity.this, "rid存在，可以跳转哦！", Toast.LENGTH_SHORT).show();
							//跳转的时候传递一个rid  在详情页面好加载数据
							Intent intent = new Intent(MyDealStateActivity.this,PropertyDetailsActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("rid", dealStateModel.Result.get(position).rid);// user
							intent.putExtras(bundle);
							startActivity(intent);
							finish();
							
						}
						
						
					}
				});
				//----------------------------------分割线----------------------------------
				
				for (int i = 0; i < houseMessages.size(); i++) {
					int housestate = Integer.parseInt(houseMessages.get(i));
					switch (housestate) {
					case 1:
						Toast.makeText(MyDealStateActivity.this, "诚意金", Toast.LENGTH_SHORT).show();
						
						break;
					case 2:
						Toast.makeText(MyDealStateActivity.this, "签署合同", Toast.LENGTH_SHORT).show();
								
						break;
					case 3:
						Toast.makeText(MyDealStateActivity.this, "检查房屋", Toast.LENGTH_SHORT).show();
				
						break;
					case 4:
						Toast.makeText(MyDealStateActivity.this, "支付房款", Toast.LENGTH_SHORT).show();
				
						break;
					case 7:
						Toast.makeText(MyDealStateActivity.this, "购买保险", Toast.LENGTH_SHORT).show();
						
						break;
					case 8:
						Toast.makeText(MyDealStateActivity.this, "交接过户", Toast.LENGTH_SHORT).show();
			
						break;

					default:
						break;
					}
					
				}
				
				
				break;

			default:
				break;
			}
		}
	};
	private ImageButton ivBack;
	private TextView tvTitle;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_my_deal_state);
		initView();
		loadDataFromNet();
		
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
	protected void initView() {
		
		ivBack = (ImageButton)findViewById(R.id.imageButton_left);
		tvTitle = (TextView)findViewById(R.id.textView_title);
		ivBack.setOnClickListener(this);
		tvTitle.setText("交易状态");
		
		mListView = (ListView) findViewById(R.id.listView_content);
		
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.imageButton_left:
			finish();
			break;
		case R.id.imageButton_right:
		case R.id.button_right:
			break;
//			点击图片  如果rid存在  则根据"http://gotyh.com/tianyuanadmin/index.php/Home/Index/index?id="+SPUtils.getString(MyDealStateActivity.this,
//			"user_id")+"&rid="+dealStateModel.Result.get(0).rid;请求数据 跳转页面
//			Toast.makeText(MyDealStateActivity.this, "rid存在，可以请求数据哦！", Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent(this,PropertyDetailsActivity.class);
//			startActivity(intent);
			
		}
	}

private void loadDataFromNet() {
	// 创建okHttpClient对象
	OkHttpClient mOkHttpClient = new OkHttpClient();
	
	// 创建一个Request
	final Request request = new Request.Builder().url(
			"http://gotyh.com/tianyuanadmin/index.php/Home/index/Transaction?id="+SPUtils.getString(this, "user_id"))
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
			//final LBMainAssetsModel goodonedata1 = JSON.parseObject(roomMessage, LBMainAssetsModel.class);
			LBDealStateModel dealStateModel = new Gson().fromJson(
					roomMessage, LBDealStateModel.class);
			Message msg = mhandler.obtainMessage();
			msg.what = DEAL_STATE;
			msg.obj = dealStateModel;
			mhandler.sendMessage(msg);

		}
	});
		
	}

}
