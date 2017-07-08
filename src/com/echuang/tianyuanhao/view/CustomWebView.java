package com.echuang.tianyuanhao.view;

import com.echuang.tianyuanhao.listview.Pullable;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class CustomWebView extends WebView implements Pullable{

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void loadUrl(String url) {
		super.loadUrl(url);
	}

	@Override
	public boolean canPullDown() {
		if(getScrollY() == 0){
			return true;
		}
		return false;
	}

}
