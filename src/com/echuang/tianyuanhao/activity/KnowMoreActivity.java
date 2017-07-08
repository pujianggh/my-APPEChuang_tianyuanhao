package com.echuang.tianyuanhao.activity;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.utils.SPUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class KnowMoreActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private ImageButton ivBack;
	private TextView tvTitle;
	private CheckBox cbManSex;
	private CheckBox cbWoManSex;
	public boolean sexFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_know_more);
		sexFlag = SPUtils.getBoolean(this, "sexFlag");
		System.out.println("选择性别=============>>>"+sexFlag);
		initView();
		// 选择性别

	}

	private void initView() {
		ivBack = (ImageButton) findViewById(R.id.iv_shop_card_detail);
		tvTitle = (TextView) findViewById(R.id.tv_detail_title);
		cbManSex = (CheckBox) findViewById(R.id.cb_man);
		cbWoManSex = (CheckBox) findViewById(R.id.cb_woman);
		cbManSex.setOnCheckedChangeListener(this);
		cbWoManSex.setOnCheckedChangeListener(this);
		getSex();
		tvTitle.setText("了解更多");
		ivBack.setOnClickListener(this);

	}

	private void getSex() {
		
		
		if (sexFlag == false) {
			cbManSex.setChecked(true);
			cbWoManSex.setChecked(false);
		}else if (sexFlag == true) {
			cbManSex.setChecked(false);
			cbWoManSex.setChecked(true);
		}
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			finish();
			break;

		default:
			break;
		}

	}

	// 控制性别的选择
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.cb_man:
			//我选他的时候  sexFlag = false;
			
			
				if (isChecked) {
					cbManSex.setChecked(true);
					cbWoManSex.setChecked(false);
					sexFlag = false;
				}				
			
			
			break;
		case R.id.cb_woman:
			//我选他的时候  sexFlag = true;
			
			
				if (isChecked) {
					cbManSex.setChecked(false);
					cbWoManSex.setChecked(true);
					sexFlag = true;
				}
			
			

			break;

		default:
			break;
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SPUtils.put(this, "sexFlag", sexFlag);
	}

}
