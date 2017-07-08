package com.echuang.tianyuanhao.activity.manager.detail;


import com.echuang.tianyuanhao.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class CallManagerActivity extends Activity implements OnClickListener{
	private ImageButton ivCallManagerBack;
	private TextView tvContentTitle;
	private TextView tvTitle;
	private TextView tvFirstTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_call_manager);
		initView();
	}

	private void initView() {
		ivCallManagerBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		tvFirstTitle = (TextView)findViewById(R.id.tv_detail_title);
		tvContentTitle = (TextView)findViewById(R.id.tv_call_manager);
		tvTitle = (TextView)findViewById(R.id.tv_detail_title);
		tvTitle.setVisibility(View.GONE);
		tvContentTitle.setText("你有任何问题\n"+"可以联系物管员");
		ivCallManagerBack.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			finish();
			break;
		case R.id.tv_call_manager:
			
			break;
			
			

		default:
			break;
		}
		
	}

}
