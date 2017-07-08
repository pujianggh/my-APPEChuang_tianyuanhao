package com.echuang.tianyuanhao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.model.EarningModel;

/**
 * 资产排名
 * 
 * @ClassName: AssetsRankListAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-14 上午9:41:24
 */
public class AssetsRankListAdapter extends BaseAdapter {
	private List<EarningModel> datas;
	private Context mContext;
	private ViewHolder holder;
	private String mStatus = "";// 保存类型状态

	public void setData(List<EarningModel> datas) {
		this.datas = datas;
	}

	public AssetsRankListAdapter(Context context, String mStatus) {
		super();
		this.mContext = context;
		this.mStatus = mStatus;
	}

	@Override
	public int getCount() {
		if (datas == null) {
			return 0;
		}
		return datas.size();
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_assets_rank, null);
			holder.img_mark = (ImageView) convertView
					.findViewById(R.id.img_mark);
			holder.tv_code = (TextView) convertView.findViewById(R.id.tv_code);
			holder.tv_phone = (TextView) convertView
					.findViewById(R.id.tv_phone);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.tv_money);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EarningModel mEarningModel = (EarningModel) datas.get(position);
		if (mEarningModel != null && holder != null) {
			if ((position == 0 || position == 1|| position == 2) && mStatus.equals("1")) {
				holder.img_mark.setVisibility(View.VISIBLE);
				holder.tv_code.setVisibility(View.GONE);
			} else if (position == 0 && mStatus.equals("2")) {
				holder.img_mark.setVisibility(View.VISIBLE);
				holder.tv_code.setVisibility(View.GONE);
			} else {
				holder.img_mark.setVisibility(View.GONE);
				holder.tv_code.setVisibility(View.VISIBLE);
			}
			holder.tv_code.setText((position + 1) + "");
		}
		return convertView;
	}

	/**
	 * 组件对象
	 * 
	 * @ClassName: ViewHolder
	 * @Description: TODO
	 * @author 蒲江
	 * @date 2016-7-14 上午10:03:15
	 */
	private class ViewHolder {
		public TextView tv_code, tv_phone, tv_money;
		public ImageView img_mark;
	}

}
