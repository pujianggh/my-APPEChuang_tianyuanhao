package com.echuang.tianyuanhao.adapter;


import java.util.List;

import com.echuang.tianyuanhao.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DetailPagerAdapter extends PagerAdapter {
	
	private List<ImageView> imgs;
	private String[] imgurls;
	
	private Context mContext;
	
	
	public DetailPagerAdapter(List<ImageView> imageViews,String[] imageUrls, Context context) {
		imgs = imageViews;
		imgurls = imageUrls;
		mContext = context;		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {	//这个方法用来实例化页卡
		imgs.get(position).setScaleType(ImageView.ScaleType.FIT_XY);
		Picasso.with(mContext).load(imgurls[position]).into(imgs.get(position));
		 container.addView(imgs.get(position), 0);		 
		 //添加页卡
		 return imgs.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(imgs.get(position));
	}

}
