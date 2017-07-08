package com.echuang.tianyuanhao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;

/**
 * 首页查询
 * 
 * @ClassName: SearchActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-19 下午1:51:09
 */
public class SearchActivity extends BaseFragmentActivity implements
		OnClickListener {
	private Button mSearch;
	private ImageView mClose, mCountryAo, mCountryEn, mCountryJp, mCountryUSA;
	private TextView mPrice;
	private SeekBar mPriceArea;
	private int countryCode = 1;// 默认

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_search);
		initView();
	}

	@Override
	protected void initView() {
		mSearch = (Button) findViewById(R.id.btn_search);
		mClose = (ImageView) findViewById(R.id.igv_close);
		mPrice = (TextView) findViewById(R.id.tv_price);
		mPriceArea = (SeekBar) findViewById(R.id.sb_price_area);
		mCountryAo = (ImageView) findViewById(R.id.igv_country_ao);
		mCountryEn = (ImageView) findViewById(R.id.igv_country_en);
		mCountryJp = (ImageView) findViewById(R.id.igv_country_jp);
		mCountryUSA = (ImageView) findViewById(R.id.igv_country_usa);
		mSearch.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mCountryAo.setOnClickListener(this);
		mCountryEn.setOnClickListener(this);
		mCountryJp.setOnClickListener(this);
		mCountryUSA.setOnClickListener(this);
		mPriceArea
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						int width = mPriceArea.getWidth();
						int disp = width * progress / 100;
						if (progress >= 90) {
							mPrice.setText("不限");
							mPrice.setPadding(disp-5, 0, 0, 0);
						} else {
							mPrice.setText("$" + progress + "万");
							mPrice.setPadding(disp, 0, 0, 0);
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.igv_country_ao:
			countryCode = 2;
			setCountryIgv();
			break;
		case R.id.igv_country_en:
			countryCode = 3;
			setCountryIgv();
			break;
		case R.id.igv_country_jp:
			countryCode = 4;
			setCountryIgv();
			break;
		case R.id.igv_country_usa:
			countryCode = 1;
			setCountryIgv();
			break;
		case R.id.igv_close:
			back();
			break;
		case R.id.btn_search:
			startActivity(new Intent(this, SearchResultActivity.class));
			finish();
			break;
		case R.id.imageButton_left:
			back();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void back() {
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	protected void next() {
	}

	private void setCountryIgv() {
		mCountryAo.setImageResource(R.drawable.ic_search_country_ao);
		mCountryEn.setImageResource(R.drawable.ic_search_country_en);
		mCountryJp.setImageResource(R.drawable.ic_search_country_jp);
		mCountryUSA.setImageResource(R.drawable.ic_search_country_usa);
		if (countryCode == 1) {
			mCountryUSA
					.setImageResource(R.drawable.ic_search_country_usa_pressed);
		} else if (countryCode == 2) {
			mCountryAo
					.setImageResource(R.drawable.ic_search_country_ao_pressed);
		} else if (countryCode == 3) {
			mCountryEn
					.setImageResource(R.drawable.ic_search_country_en_pressed);
		} else if (countryCode == 4) {
			mCountryJp
					.setImageResource(R.drawable.ic_search_country_jp_pressed);
		}
	}

}