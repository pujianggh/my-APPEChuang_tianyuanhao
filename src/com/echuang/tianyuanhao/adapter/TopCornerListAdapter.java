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
public class TopCornerListAdapter extends BaseAdapter {
	private List<CornerModel> datas;
	private Context context;
	private ViewHolder holder;
	private int index = -1;
	private boolean isNeedSelected = false;
	private boolean isNeedArrow = true;

	public void setData(List<CornerModel> datas) {
		this.datas = datas;
	}

	public TopCornerListAdapter(Context context) {
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_topcorner, null);
			holder.tv = (TextView) convertView.findViewById(R.id.t_card_type);
			holder.t_card_state = (ImageView) convertView
					.findViewById(R.id.t_card_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(datas.get(position).getContent());
		if (isNeedSelected) {
			holder.t_card_state.setVisibility(View.VISIBLE);
			if (position == index) {
				if (datas.get(position).getFlag() == 0) {
					holder.tv.setTextColor(context.getResources().getColor(
							R.color.text_blue));
				} else {
					holder.tv.setTextColor(context.getResources().getColor(
							R.color.text_blue));
				}
			} else {
				holder.tv.setTextColor(context.getResources().getColor(
						R.color.text_gray));
			}
		}
		return convertView;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isNeedSelected() {
		return isNeedSelected;
	}

	public void setNeedSelected(boolean isNeedSelected) {
		this.isNeedSelected = isNeedSelected;
	}

	public static class ViewHolder {
		public TextView tv;
		public ImageView t_card_state;
	}

	/**
	 * 重新设置数据参数
	 * 
	 * @Title: setDataFlag
	 * @Description: TODO
	 * @param @param index
	 * @return void
	 * @throws
	 */
	public void setDataFlag(int index) {
		if (datas.get(index).getFlag() == 0) {
			datas.get(index).setFlag(1);
		} else {
			datas.get(index).setFlag(0);
		}
		for (int i = 0; i < datas.size(); i++) {
			if (index != i) {
				datas.get(i).setFlag(0);
			}
		}
	}

	public boolean isNeedArrow() {
		return isNeedArrow;
	}

	public void setNeedArrow(boolean isNeedArrow) {
		this.isNeedArrow = isNeedArrow;
	}

}
