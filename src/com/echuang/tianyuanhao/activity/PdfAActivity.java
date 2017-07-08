package com.echuang.tianyuanhao.activity;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.instance.LBUrls;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PdfAActivity extends Activity{
	
	
	private WebView wvPdf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdfa);
		initView();
	}

	private void initView() {
		
		Bundle pdfaBundle = getIntent().getExtras();
		String pdfaUrl = pdfaBundle.getString("pdfUrla");
		wvPdf = (WebView)findViewById(R.id.wv_pdfa);
		String url = LBUrls.ALL_IMG_PRE_URL+pdfaUrl;
		wvPdf.loadUrl(url);
		
	}

}
