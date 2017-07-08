package com.echuang.tianyuanhao.activity;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.model.LBMainAssetsModel.Resultdata;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class LinkActivity extends Activity implements OnClickListener {

	private WebView webView;
	private String webUrl;
	private ImageButton ivBack;
	private TextView tvTitle;
	private TextView tvEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lick);
		getBundle();
		initView();
	}

	public void getBundle() {
		webUrl = this.getIntent().getStringExtra("webUrl");
	}

	private void initView() {
		ivBack = (ImageButton)findViewById(R.id.iv_shop_card_detail);
		tvTitle = (TextView)findViewById(R.id.tv_detail_title);
		tvEdit = (TextView)findViewById(R.id.tv_edit);
		ivBack.setOnClickListener(this);
		tvTitle.setText("");
		
		webView = (WebView) findViewById(R.id.wv_lick);
		webView.loadUrl(webUrl);

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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webView.destroy();
	}

}
