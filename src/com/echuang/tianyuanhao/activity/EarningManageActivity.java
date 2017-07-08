package com.echuang.tianyuanhao.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.earning.PropertyInfoActivity;
import com.echuang.tianyuanhao.activity.earning.RenterManagerActivity;
import com.echuang.tianyuanhao.activity.manager.detail.CallManagerActivity;
import com.echuang.tianyuanhao.activity.manager.detail.EarningManagerSellHouseActivity;
import com.echuang.tianyuanhao.activity.manager.detail.EarningManagerServiceStateActivity;
import com.echuang.tianyuanhao.activity.manager.detail.EarningManagerServiceStateAnotherActivity;
import com.echuang.tianyuanhao.model.ADInfo;
import com.echuang.tianyuanhao.utils.SharedPreferencesUtils;
import com.echuang.tianyuanhao.utils.StrRes;
import com.echuang.tianyuanhao.view.pager.CycleViewPager.ImageCycleViewListener;
import com.echuang.tianyuanhao.view.pager.CycleViewPager2;
import com.echuang.tianyuanhao.view.pager.ViewFactory;

/**
 * 收益详情管理
 * 
 * @ClassName: EarningManageActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-15 上午10:26:05
 */
public class EarningManageActivity extends Activity implements OnClickListener {

	private boolean boolean1 = false;

	private LinearLayout mPropertyInfo, mTenantManage, mCallProperty,
			mEarningsSearch, mPayDutyInfo, mSellProperty;
	private ImageButton mBack;
	private TextView mTitle;

	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private List<ADInfo> adInfos = new ArrayList<ADInfo>();
	private CycleViewPager2 mCycleViewPager;

	private String[] imageUrls = { "http://tianyuanhao.oss-cn-shanghai.aliyuncs.com/img/2016-07-14/46189519371594491620160928.png" };
	private LinearLayout mSellHouse;
	private LinearLayout mServicestate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_earning_manage);
		initViews();
		initImageData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lly_property_info:// 物业信息
			startActivity(new Intent(this, PropertyInfoActivity.class)
					.putExtra(StrRes.title, "物业信息").putExtra(StrRes.content,
							"物业信息"));
			break;
		case R.id.lly_tenant_manage:// 租客管理
			startActivity(new Intent(this, RenterManagerActivity.class)
					.putExtra(StrRes.title, "租客管理").putExtra(StrRes.content,
							"租客管理"));

			break;
		case R.id.lly_call_property:// 收益查询
			startActivity(new Intent(this, MyStoreUpActivity.class).putExtra(
					StrRes.title, "收益查询").putExtra(StrRes.content, "收益查询"));
			break;
		case R.id.lly_earnings_search:// 物管秘書
			startActivity(new Intent(this, HouseClerkActivity.class));

			break;
		case R.id.lly_pay_duty_info:// 出售物業
			startActivity(new Intent(this, CallManagerActivity.class));

			break;
		case R.id.lly_sell_property:// 納稅信息
			startActivity(new Intent(this, MyStoreUpActivity.class).putExtra(
					StrRes.title, "纳税信息").putExtra(StrRes.content, "纳税信息"));

			break;
		case R.id.lly_pay_duty_info_add:// 出售物业
			startActivity(new Intent(this,
					EarningManagerSellHouseActivity.class));

			break;

		/**
		 * @Description: TODO 有个BUG没解决好
		 */
		case R.id.lly_sell_property_add:// 服务状态
			Boolean boolean1 = (Boolean) SharedPreferencesUtils.getParam(this,
					"isOpenState", false);
			if (!boolean1) {
				boolean1 = true;
				SharedPreferencesUtils.setParam(this, "isOpenState", boolean1);
				startActivity(new Intent(this,
						EarningManagerServiceStateActivity.class));

			} else {
				boolean1 = false;
				SharedPreferencesUtils.setParam(this, "isOpenState", boolean1);
				startActivity(new Intent(this,
						EarningManagerServiceStateAnotherActivity.class));

			}
			

			break;

		case R.id.igbtn_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		}
	}

	/**
	 * 初始化图片数据
	 * 
	 * @Title: initImageData
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initImageData() {
		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i);
			adInfos.add(info);
		}
		// 将最后一个ImageView添加进来
		imageViews.add(ViewFactory.getImageView(this,
				adInfos.get(adInfos.size() - 1).getUrl()));
		for (int i = 0; i < adInfos.size(); i++) {
			imageViews.add(ViewFactory.getImageView(this, adInfos.get(i)
					.getUrl()));
		}
		// 将第一个ImageView添加进来
		imageViews.add(ViewFactory.getImageView(this, adInfos.get(0).getUrl()));
		// 设置循环，在调用setData方法前调用
		mCycleViewPager.setCycle(true);
		// 在加载数据前设置是否循环
		mCycleViewPager.setData(imageViews, adInfos,
				new CycleViewPager2.ImageCycleViewListener() {

					@Override
					public void onImageClick(ADInfo info, int postion,
							View imageView) {

					}
				});
		// 设置轮播
		mCycleViewPager.setWheel(true);
		// 设置轮播时间，默认5000ms
		mCycleViewPager.setTime(5000);
		// 设置圆点指示图标组居中显示，默认靠右
		mCycleViewPager.setIndicatorCenter();
	}

	/**
	 * 组件初始化
	 * 
	 * @Title: initViews
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	@SuppressLint("NewApi")
	private void initViews() {
		mCycleViewPager = (CycleViewPager2) getFragmentManager()
				.findFragmentById(R.id.earn_fragment_viewpager);
		mPropertyInfo = (LinearLayout) findViewById(R.id.lly_property_info);
		mTenantManage = (LinearLayout) findViewById(R.id.lly_tenant_manage);
		mCallProperty = (LinearLayout) findViewById(R.id.lly_call_property);
		mEarningsSearch = (LinearLayout) findViewById(R.id.lly_earnings_search);
		mPayDutyInfo = (LinearLayout) findViewById(R.id.lly_pay_duty_info);
		mSellProperty = (LinearLayout) findViewById(R.id.lly_sell_property);
		mSellHouse = (LinearLayout) findViewById(R.id.lly_pay_duty_info_add);
		mServicestate = (LinearLayout) findViewById(R.id.lly_sell_property_add);

		mBack = (ImageButton) findViewById(R.id.igbtn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mTitle.setText(getIntent().getStringExtra(StrRes.title));
		mBack.setOnClickListener(this);
		mPropertyInfo.setOnClickListener(this);
		mTenantManage.setOnClickListener(this);
		mSellProperty.setOnClickListener(this);
		mEarningsSearch.setOnClickListener(this);
		mPayDutyInfo.setOnClickListener(this);
		mCallProperty.setOnClickListener(this);
		mSellHouse.setOnClickListener(this);
		mServicestate.setOnClickListener(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return super.onKeyDown(keyCode, event);
	}
}
