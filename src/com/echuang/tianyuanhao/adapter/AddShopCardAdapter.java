package com.echuang.tianyuanhao.adapter;



import com.echuang.tianyuanhao.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AddShopCardAdapter extends BaseAdapter {

	//备用集合
	//private List<HouseInfo> mList;
	private Context mContext;
	public AddShopCardAdapter() {
		// TODO Auto-generated constructor stub
	}

	public AddShopCardAdapter( Context activity) {
		mContext = activity;
	}

	@Override
	public int getCount() {

		return 20;
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
		
		if (convertView== null) {
			convertView = View.inflate(mContext, R.layout.add_shopcar_item,null);
		}
		
		
		
		return convertView;
	}

}
