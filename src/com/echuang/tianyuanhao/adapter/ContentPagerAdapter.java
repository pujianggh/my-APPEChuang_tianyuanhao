package com.echuang.tianyuanhao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 分类-Fragment分类
 * 
 * @ClassName: ContentPagerAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-13 下午6:05:27
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {
	private Fragment[] fragments;

	public ContentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}
}