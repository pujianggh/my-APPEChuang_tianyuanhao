package com.echuang.tianyuanhao.activity;

import java.util.ArrayList;
import java.util.List;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.R.drawable;
import com.echuang.tianyuanhao.adapter.AboutWeAdapter;
import com.echuang.tianyuanhao.fragment.AboutFragment;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class AboutWeActivity extends Activity {

	private ListView lvAboutWe;
	public List<Bitmap> list = new ArrayList<Bitmap>();
	private double height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_we);
		
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		double width = dm.widthPixels;
		height = width*2.028;
		
		Resources r = this.getResources();
		Bitmap bmpone = BitmapFactory.decodeResource(r, R.drawable.bigone);
		Bitmap bmptwo = BitmapFactory.decodeResource(r, R.drawable.bigtwo);
		Bitmap bmpthree = BitmapFactory.decodeResource(r, R.drawable.bigthree);
		Bitmap bmpfore = BitmapFactory.decodeResource(r, R.drawable.bigfore);

		list.add(bmpone);
		list.add(bmptwo);
		list.add(bmpthree);
		list.add(bmpfore);
		initView();
	}

	private void initView() {
		
		lvAboutWe = (ListView) findViewById(R.id.lv_about_we);
		lvAboutWe.setDividerHeight(0);
		lvAboutWe.setAdapter(new AboutWeAdapter(AboutWeActivity.this, list,height));

	}

}
