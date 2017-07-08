package com.echuang.tianyuanhao.fragment;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.ContentPagerAdapter;
import com.echuang.tianyuanhao.base.BaseFragment;
import com.echuang.tianyuanhao.utils.StrRes;

/**
 * 动态界面
 * 
 * @ClassName: DynamicFragment
 * @Description: TODO
 * @author 蒲江
 * @date 2015-7-31 下午2:01:59
 */
public class DynamicFragment extends BaseFragment {
	public static DynamicFragment instance = null;
	protected View parentView;
	private ViewPager pager;
	private HorizontalScrollView mHorizontalScrollView;
	private LinearLayout mLinearLayout;
	private int mScreenWidth;
	private ImageView mImageView;
	private int item_width;
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	private int endPosition;
	private int beginPosition;
	private int currentFragmentIndex = 0;
	private boolean isEnd;
	private TextView view;
	public Fragment[] fragments;

	// 头部布局
	private ContentPagerAdapter mContentPagerAdapter = null;
	private ArrayList<TextView> titleList = new ArrayList<TextView>();
	private List<String> titles = new ArrayList<String>();// 导航栏数据
	private List<String> types = new ArrayList<String>();// 导航栏数据
	private String[] titleStr = {"全部","公司动态","原创报告","行业动态","政策法规"};
	private String[] typeStr = {"qyzx","rxl","rxl","rxl","rxl"};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fr_dynamic, container, false);
		initView();	
		return parentView;
	}

	@Override
	protected void initView() {
		((ImageButton) parentView.findViewById(R.id.imageButton_left)).setVisibility(View.INVISIBLE);
		((TextView) parentView.findViewById(R.id.textView_title)).setText(getResources().getString(R.string.h_dynamic));
		
		pager = (ViewPager) parentView.findViewById(R.id.pager);
		mHorizontalScrollView = (HorizontalScrollView) parentView
				.findViewById(R.id.hsv_view);
		mLinearLayout = (LinearLayout) parentView
				.findViewById(R.id.hsv_content);
		mImageView = (ImageView) parentView.findViewById(R.id.img1);

		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		initNav();
		
		int count = titleStr.length;
		//改动前 Fragment[] fragments = new Fragment[count];
		fragments = new Fragment[count];
		for (int i = 0; i < count; i++) {
			Bundle bundle = new Bundle();
			bundle.putString(StrRes.status, i+"");
			fragments[i] = new DynamicPageFragment(i);
			fragments[i].setArguments(bundle);
		}
		if (mContentPagerAdapter == null) {
			mContentPagerAdapter = new ContentPagerAdapter(
					getChildFragmentManager(), fragments);
			pager.setAdapter(mContentPagerAdapter);
			//pager.setOffscreenPageLimit(count-1);
			//预加载页面的方法
			pager.setOffscreenPageLimit(count-1);
			pager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					currentFragmentIndex = position;
					setParrgeTitle(position);
					
					//标签被选中或者点击
					System.out.println("onPageSelected==========:"+currentFragmentIndex);
					
					
					Animation animation = new TranslateAnimation(endPosition, position
							* item_width, 0, 0);
					beginPosition = position * item_width;
					
					if (animation != null) {
						animation.setFillAfter(true);
						animation.setDuration(0);
						mImageView.startAnimation(animation);
						mHorizontalScrollView.smoothScrollTo((currentFragmentIndex - 1)
								* item_width, 0);
					}
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
					
					
					
					if (!isEnd) {
						if (currentFragmentIndex == position) {
							endPosition = item_width * currentFragmentIndex
									+ (int) (item_width * positionOffset);
						}
						if (currentFragmentIndex == position + 1) {
							endPosition = item_width * currentFragmentIndex
									- (int) (item_width * (1 - positionOffset));
						}

						Animation mAnimation = new TranslateAnimation(beginPosition,
								endPosition, 0, 0);
						mAnimation.setFillAfter(true);
						mAnimation.setDuration(0);
						mImageView.startAnimation(mAnimation);
						mHorizontalScrollView.invalidate();
						beginPosition = endPosition;
					}
					
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					
					
					
					if (state == ViewPager.SCROLL_STATE_DRAGGING) {
						isEnd = false;
					} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
						isEnd = true;
						beginPosition = currentFragmentIndex * item_width;
						if (pager.getCurrentItem() == currentFragmentIndex) {
							// 未跳入下一个页面
							mImageView.clearAnimation();
							Animation animation = null;
							// 恢复位置
							animation = new TranslateAnimation(endPosition,
									currentFragmentIndex * item_width, 0, 0);
							animation.setFillAfter(true);
							animation.setDuration(1);
							mImageView.startAnimation(animation);
							mHorizontalScrollView.invalidate();
							endPosition = currentFragmentIndex * item_width;
						}
					}
				}
			});
		}
	}

	/**
	 * 设置导航栏，title
	 * 
	 * @Title: initNav
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initNav() {
		titles.clear();
		for (int i = 0; i < titleStr.length; i++) {
			titles.add(titleStr[i]);
		}
		for (int i = 0; i < typeStr.length; i++) {
			types.add(typeStr[i]);
		}
		float number = 4.0f;// 
		List<String> templist = new ArrayList<String>();
		List<String> temptype = new ArrayList<String>();
		titles.removeAll(templist);
		types.removeAll(temptype);
		item_width = (int) ((mScreenWidth / number + 0.5f));
		mImageView.getLayoutParams().width = item_width;
		titleList.clear();
		mLinearLayout.removeAllViews();
		for (int i = 0; i < titles.size(); i++) {
			RelativeLayout layout = new RelativeLayout(getActivity());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			view = new TextView(getActivity());
			view.setText(titles.get(i));
			view.setTextSize(14);
			view.setTextColor(getResources().getColor(R.color.text_gray));
			if (columnSelectIndex == i) {
				
				view.setTextColor(getResources().getColor(R.color.pic_text_orange));
			}
			titleList.add(view);
			layout.addView(view, params);
			// (int) (mScreenWidth / 5+ 0.5f) 这里的5代表当前界面显示几个table
			mLinearLayout.addView(layout, (int) (mScreenWidth / number + 0.5f),
					LayoutParams.MATCH_PARENT);
			layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
						

					pager.setCurrentItem((Integer) v.getTag());
					
				}
			});
			layout.setTag(i);
		}
	}
	
	/**
	 * 
	 * @Title: setParrgeTitle
	 * @Description: TODO 
	 * @param @param position
	 * @return void
	 * @throws
	 */
	private void setParrgeTitle(int position) {
		columnSelectIndex = position;
		for (int i = 0; i < titleList.size(); i++) {
			view = titleList.get(i);
			view.setTextColor(getResources().getColor(R.color.text_gray));
			if (i == position) {
				view.setTextColor(getResources().getColor(R.color.orange));
			} 
		}
	}

	@Override
	protected void back() {
	}

	@Override
	protected void next() {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
}
