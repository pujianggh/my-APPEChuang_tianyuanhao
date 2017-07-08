package com.echuang.tianyuanhao.activity.manager.detail;


import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.utils.SharedPreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EarningManagerServiceStateActivity extends Activity implements OnClickListener{
	
	private ImageView ivServiceState;
	private TextView tvServiceContent;
	private Button btCloseService;
	private ImageButton ivServiceStateBack;
	private TextView tvServiceStateTitle;
	private TextView tvServiceState;

	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sell_state);
		initView();
	}

	private void initView() {
		ivServiceStateBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		tvServiceStateTitle = (TextView)findViewById(R.id.tv_detail_title);
		ivServiceState = (ImageView)findViewById(R.id.iv_service_state);
		tvServiceState = (TextView)findViewById(R.id.tv_isopen_service);
		
		
		tvServiceContent = (TextView)findViewById(R.id.tv_service_content);
		btCloseService = (Button)findViewById(R.id.bt_close_service);
		
		tvServiceStateTitle.setText("服务状态");
		tvServiceContent.setText("1、一见钟情 爱上你 二话没说 就追你\n"+
									"\n2、三言两语 骗了你 死皮懒脸 跟着你\n"+
									"\n3、跳舞场上 抱着你 溜冰场上 拥着你\n"+
									"\n4、其实我 并不爱你 巴不得 甩了你\n"
									);
		btCloseService.setText("暂停服务");
		ivServiceStateBack.setOnClickListener(this);
		btCloseService.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shop_card_detail:
			this.finish();
			break;
		case R.id.bt_close_service:
			
			finish();
					
			break;

		default:
			break;
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
}
