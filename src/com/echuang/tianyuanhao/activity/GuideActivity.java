package com.echuang.tianyuanhao.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.base.BaseFragmentActivity;
import com.echuang.tianyuanhao.utils.LogAPP;
import com.echuang.tianyuanhao.utils.SharedPreUtils;

/**
 * 引导界面
 * @ClassName: GuideActivity
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 下午2:07:35
 */
public class GuideActivity extends BaseFragmentActivity{

	private ViewPager viewpager;
	private ViewPagerAdapter mVpAdapter;
	public static int currentItem = 0;
	private ViewGroup group;
	//引导图片
	private List<View>imageViews;
	//引导图片资源id
	private int[] imageResId;
	//引导图片底部的点
	private ImageView[] dots;
	//开始使用按钮
	private ImageView startButton;
	//高亮点
	private int dotLight;
	//普通点
	private int dotNormal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_guide);
		initView();
	}
	
	@Override
	protected void initView() {
		//设置高点点资源
		dotLight = R.drawable.dot1;
		//设置普通点资源
		dotNormal = R.drawable.dot2;
		//设置引导页图片资源
		imageResId=new int[]{R.drawable.guide1, R.drawable.guide1, R.drawable.guide1, R.drawable.guide1};

		imageViews=new ArrayList<View>();
		Resources res=getResources();
		for(int i=0;i<imageResId.length;i++){
			ImageView imageview=new ImageView(this);
			Bitmap bitmap=BitmapFactory.decodeResource(res, imageResId[i]);
			imageview.setImageBitmap(bitmap);
			imageview.setScaleType(ScaleType.FIT_XY);
			imageViews.add(imageview);
		}
		
		dots = new ImageView[imageViews.size()];  
		group = (ViewGroup) findViewById(R.id.viewGroup);  
		int size = getResources().getDimensionPixelSize(R.dimen.dotsize);
		for (int i = 0; i < imageViews.size(); i++) {  
			ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(size,size));  
            imageView.setPadding(size, 0, size, 0);  
            dots[i] = imageView;  
            if (i == 0) {  
            	dots[i].setBackgroundResource(dotLight);  
            } else {  
            	dots[i].setBackgroundResource(dotNormal);  
            }  
            group.addView(dots[i]);  
        }  
		
		viewpager=(ViewPager)findViewById(R.id.vp);
		mVpAdapter = new ViewPagerAdapter();
		viewpager.setAdapter(mVpAdapter);
		viewpager.setOnPageChangeListener(new MyPageChangeListener());
		
		startButton=(ImageView)findViewById(R.id.begainButton);
		startButton.setVisibility(View.GONE);
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreUtils.getInstance(GuideActivity.this).saveFirstEnter(false);
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
			}
		});
	}

	@Override
	protected void next() {
		
	}

	@Override
	protected void back() {
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	private class ViewPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView(imageViews.get(arg1));  
        }  

		@Override
		public Object instantiateItem(View arg0, int arg1) {  
            ((ViewPager) arg0).addView(imageViews.get(arg1));  
            return imageViews.get(arg1);  
        }  
	}
	
	
	public class MyPageChangeListener implements OnPageChangeListener{
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			LogAPP.i("hjb", "onPageSelected"+arg0);
			for (int i = 0; i < dots.length; i++) {  
				dots[i].setBackgroundResource(dotNormal);
                if (i == arg0) {  
                	dots[i].setBackgroundResource(dotLight);  
                }  
            }
			if(arg0 == (dots.length-1)){
				group.setVisibility(View.GONE);
            	startButton.setVisibility(View.VISIBLE);
            }else{
            	group.setVisibility(View.GONE);
            	startButton.setVisibility(View.GONE);
            }
		}
	}

}

