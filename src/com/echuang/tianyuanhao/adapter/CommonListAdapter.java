package com.echuang.tianyuanhao.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.model.CornerModel;

/**
 * 字符串列表适配器
 * 
 * @ClassName: TopCornerListAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-9-1 上午11:04:32
 */
public class CommonListAdapter extends BaseAdapter {
	private List<CornerModel> datas;
	private Context context;
	private ViewHolder holder;

	public void setData(List<CornerModel> datas) {
		this.datas = datas;
	}

	public CommonListAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		if (datas != null) {
			return datas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_common_select, null);
			holder.tv = (TextView) convertView.findViewById(R.id.t_card_type);
			holder.t_card_state = (ImageView) convertView
					.findViewById(R.id.t_card_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(datas.get(position).getContent());
		return convertView;
	}

	public static class ViewHolder {
		public TextView tv;
		public ImageView t_card_state;
	}
}
