package com.echuang.tianyuanhao.activity;


import java.util.ArrayList;
import java.util.List;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.AddShopCardAdapter;
import com.echuang.tianyuanhao.model.HouseInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopCardActivity extends Activity implements OnItemClickListener,OnClickListener{
	
	private ListView addshopcardLv;
	private List<HouseInfo> list = new ArrayList<HouseInfo>();
	public Context mContext;
	private TextView tvShopTitle;
	private TextView tvEdit;
	private ImageButton ivshopcarddetailBack;
	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_card);
		mContext = getApplicationContext();
		initData();
		initView();
		
	}

	//加载数据
	private void initData() {
		
		
	}

	private void initView() {
		//初始化头信息
		ivshopcarddetailBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		tvShopTitle = (TextView)findViewById(R.id.tv_detail_title);
		tvEdit = (TextView)findViewById(R.id.tv_edit);
		btnNext = (Button)findViewById(R.id.bt_next);
		tvShopTitle.setText("购物车");
		tvShopTitle.setTextSize(18);
		tvEdit.setText("编辑");
		tvEdit.setTextSize(15);
		
		ivshopcarddetailBack.setOnClickListener(this);
		tvEdit.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		
		addshopcardLv = (ListView)findViewById(R.id.lv_shop_item);
		addshopcardLv.setAdapter(new AddShopCardAdapter(mContext));
		addshopcardLv.setOnItemClickListener(this);
		
		
	}
	
	//当前页面的普通控件的点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			this.finish();
			break;
		case R.id.tv_edit:
			Toast.makeText(mContext, "点击了编辑按钮", Toast.LENGTH_SHORT).show();
			break;
		case R.id.bt_next:
			Toast.makeText(mContext, "点击了编辑按钮", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}
	
	//listview中的item点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(mContext, "我点击了海外资产的条目--"+position, Toast.LENGTH_SHORT).show();
		
	}

}
