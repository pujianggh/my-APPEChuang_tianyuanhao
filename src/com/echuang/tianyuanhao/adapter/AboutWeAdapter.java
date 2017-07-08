package com.echuang.tianyuanhao.adapter;

import java.util.List;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.R.drawable;
import com.echuang.tianyuanhao.activity.AboutWeActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


public class AboutWeAdapter extends BaseAdapter {
	
	
	 
	private List<Bitmap> mList;
	private Context mContext;
	double height ;

	public AboutWeAdapter(AboutWeActivity aboutWeActivity) {
		
	}

	public AboutWeAdapter(AboutWeActivity aboutWeActivity, List<Bitmap> list, double height) {
		mContext = aboutWeActivity;
		mList = list;  
		this.height = height;
	}

	@Override
	public int getCount() {
		
		return 4;
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
		ViewHolder vhHolder = null;
		if (convertView==null) {
			convertView = View.inflate(mContext, R.layout.about_activity_item, null);
			vhHolder = new ViewHolder();
			convertView.setTag(vhHolder);
		}
		vhHolder = (ViewHolder) convertView.getTag();
		
		
		vhHolder.ivBigImageView = (ImageView)convertView.findViewById(R.id.iv_big_image_item);
		LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) vhHolder.ivBigImageView.getLayoutParams();
		//获取当前控件的布局对象
		params.height=(int) height;//设置当前控件布局的高度
		vhHolder.ivBigImageView.setLayoutParams(params);//将设置好的布局参数应用到控件中
		vhHolder.ivBigImageView.setImageBitmap(mList.get(position));
		
		return convertView;
	}
	
	class ViewHolder{
		public ImageView ivBigImageView;
		
	}

	

}
