package com.echuang.tianyuanhao.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.ContentPagerAdapter;
import com.echuang.tianyuanhao.application.AppAppliction;
import com.echuang.tianyuanhao.base.BaseSwipeBackActivity;
import com.echuang.tianyuanhao.fragment.AssetsRankFragment;
import com.echuang.tianyuanhao.utils.StrRes;

/**
 * 资产排行
 * 
 * @ClassName: AssetsRankActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-13 下午5:08:08
 */
public class AssetsRankActivity extends BaseSwipeBackActivity {
	private static final String TAG = "AssetsRankActivity";
	private Context mContext = AssetsRankActivity.this;
	private Button mButtonTab1, mButtonTab2, mButtonTab3;
	private ContentPagerAdapter mContentPagerAdapter;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppAppliction.getInstance().addActivity(this);
		setContentView(R.layout.act_assets_rank);
		initView();
	}

	@Override
	protected void initView() {
		initTitle("排行榜", "", 0);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mButtonTab1 = (Button) findViewById(R.id.btn_tab1);
		mButtonTab2 = (Button) findViewById(R.id.btn_tab2);
		mButtonTab3 = (Button) findViewById(R.id.btn_tab3);
		mButtonTab1.setOnClickListener(this);
		mButtonTab2.setOnClickListener(this);
		mButtonTab3.setOnClickListener(this);
		setCurrentPage(0);// 默认选择第一个选项
		Fragment[] fragments = new Fragment[3];
		for (int i = 0; i < 3; i++) {
			Bundle bundle = new Bundle();
			if (i == 0) {
				bundle.putString(StrRes.status, "1");
			} else if (i == 1) {
				bundle.putString(StrRes.status, "2");
			} else if (i == 2) {
				bundle.putString(StrRes.status, "3");
			}
			fragments[i] = new AssetsRankFragment();
			fragments[i].setArguments(bundle);
		}
		mContentPagerAdapter = new ContentPagerAdapter(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(mContentPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setCurrentPage(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tab1:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.btn_tab2:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.btn_tab3:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.imageButton_left:
			back();
			break;
		default:
			break;
		}
	}

	/**
	 * 修改标签状态
	 * 
	 * @Title: setCurrentPage
	 * @Description: TODO
	 * @param @param current
	 * @return void
	 * @throws
	 */
	private void setCurrentPage(int current) {
		mButtonTab1.setSelected(false);
		mButtonTab2.setSelected(false);
		mButtonTab3.setSelected(false);
		if (current == 0) {
			mButtonTab1.setSelected(true);
		} else if (current == 1) {
			mButtonTab2.setSelected(true);
		} else if (current == 2) {
			mButtonTab3.setSelected(true);
		}
	}

	@Override
	protected void back() {
		hideInput();
		AssetsRankActivity.this.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	protected void next() {
	}

}
