package com.echuang.tianyuanhao.activity;

import com.echuang.tianyuanhao.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HouseClerkActivity extends Activity implements OnClickListener{
	
	private TextView tvQuestion1;
	private TextView tvQuestion2;
	private TextView tvQuestion3;
	private TextView tvQuestion4;
	private TextView tvQuestion5;
	private TextView tvQuestion6;
	private ImageButton ivBack;
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_house_clerk);
		initView();
	}

	private void initView() {
		ivBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView)findViewById(R.id.tv_detail_title);
		tvTitle.setText("物管秘书");
		
		
		
		tvQuestion1 = (TextView)findViewById(R.id.tv_question1);
		tvQuestion2 = (TextView)findViewById(R.id.tv_question2);
		tvQuestion3 = (TextView)findViewById(R.id.tv_question3);
		tvQuestion4 = (TextView)findViewById(R.id.tv_question4);
		tvQuestion5 = (TextView)findViewById(R.id.tv_question5);
		tvQuestion6 = (TextView)findViewById(R.id.tv_question6);
		tvQuestion1.setOnClickListener(this);
		tvQuestion2.setOnClickListener(this);
		tvQuestion3.setOnClickListener(this);
		tvQuestion4.setOnClickListener(this);
		tvQuestion5.setOnClickListener(this);
		tvQuestion6.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_question1:
			Toast.makeText(this, "物业管理咨询", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_question2:
					
					break;
		case R.id.tv_question3:
			
			break;
		case R.id.tv_question4:
			
			break;
		case R.id.tv_question5:
			
			break;
		case R.id.tv_question6:
			
			break;
		case R.id.iv_shop_card_detail:
			finish();
			break;
			
//			ivBack

		default:
			break;
		}
		
	}

}
