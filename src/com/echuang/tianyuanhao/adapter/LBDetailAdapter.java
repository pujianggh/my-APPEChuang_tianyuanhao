package com.echuang.tianyuanhao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LBDetailAdapter extends BaseAdapter {
	
	Context context;
	public List<String> asstesTempoLists ;
	public List<String> imgUrls ;
	

	public LBDetailAdapter(Context context, List<String> asstesTempoLists, List<String> imgUrls) {
		this.asstesTempoLists = asstesTempoLists;
		this.imgUrls = imgUrls;
		this.context = context;
	}
	

	@Override
	public int getCount() {
		
		if (asstesTempoLists==null) {
			return 0;
		}

		return asstesTempoLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_deal_state, null);
//			convertView = LayoutInflater.from(context).inflate(
//					R.layout.item_deal_state, null);
			holder.iv_house_state = (ImageView) convertView
					.findViewById(R.id.iv_house_state);
			holder.iv_state_type = (ImageView) convertView
					.findViewById(R.id.iv_state_type);
			convertView.setTag(holder);
			}else {
				
				holder = (ViewHolder) convertView.getTag();
			}
		if (imgUrls != null && holder != null) {
			// String url =
			// "http://img.taopic.com/uploads/allimg/166213/240512-16621669151737.jpg";
			
			ImageLoaderHelper.displayImage(LBInstance.HOME_IMG_URL
					+ imgUrls.get(position), holder.iv_house_state);
		}
		
		
		
		
		return convertView;
		
	}
	private class ViewHolder {
		
		public ImageView iv_house_state;
		public ImageView iv_state_type;
	}

}
