package com.echuang.tianyuanhao.activity.manager.detail;

import com.echuang.tianyuanhao.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EarningManagerSellHouseActivity extends Activity implements OnClickListener{
	
	private TextView tvPropertyTitle;
	private ImageButton ivPropertyBack;
	private Button btConfirmSell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sell_house_detail);
		initView();
	}

	private void initView() {
		tvPropertyTitle = (TextView)findViewById(R.id.tv_detail_title);
		ivPropertyBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		btConfirmSell = (Button)findViewById(R.id.bt_confirm_sell);
		ivPropertyBack.setOnClickListener(this);
		btConfirmSell.setOnClickListener(this);
		
		tvPropertyTitle.setText("出售房产");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			this.finish();
			break;
		case R.id.bt_confirm_sell:
			Toast.makeText(this, "你确认出售吗", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
		
	}

}
