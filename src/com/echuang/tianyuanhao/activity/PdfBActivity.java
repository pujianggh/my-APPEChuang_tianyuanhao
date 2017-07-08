package com.echuang.tianyuanhao.activity;


import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.instance.LBUrls;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class PdfBActivity extends Activity{
	
	private WebView wvPdfb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdfb);
		initView();
	}

	private void initView() {
		
		Bundle pdfbBundle = getIntent().getExtras();
		String pdfbUrl = pdfbBundle.getString("pdfUrlb");
		String url = LBUrls.ALL_IMG_PRE_URL+pdfbUrl;
		wvPdfb = (WebView) findViewById(R.id.wv_pdfb);
		wvPdfb.loadUrl(url);
		
	}

}
