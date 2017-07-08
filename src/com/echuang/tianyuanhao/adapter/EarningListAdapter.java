package com.echuang.tianyuanhao.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.model.EarningModel;
import com.echuang.tianyuanhao.model.LBEarningModel.EarningDetail;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;
import com.echuang.tianyuanhao.utils.LogAPP;

/**
 * 收益
 * 
 * @ClassName: EarningListAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-12 上午9:51:23
 */
public class EarningListAdapter extends BaseAdapter {
	private List<EarningModel> datas;
	private Context mContext;
	private ViewHolder holder;
	private List<EarningDetail> earningDetailLists;

	public void setData(List<EarningModel> datas) {
		this.datas = datas;
	}

	public EarningListAdapter(Context context,
			List<EarningDetail> earningDetailLists2) {

		this.mContext = context;
		this.earningDetailLists = earningDetailLists2;
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
					R.layout.item_earning, null);
			holder.lly_add = (LinearLayout) convertView
					.findViewById(R.id.lly_add);
			holder.lly_main = (LinearLayout) convertView
					.findViewById(R.id.lly_main);
			holder.img_houses = (ImageView) convertView
					.findViewById(R.id.img_houses);
			holder.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			
			holder.tv_rent_state = (TextView)convertView.findViewById(R.id.tv_rent_state);
			holder.tv_squar = (TextView)convertView.findViewById(R.id.tv_squar);
			holder.tv_bedroom = (TextView)convertView.findViewById(R.id.tv_bedroom);
			holder.tv_bathroom = (TextView)convertView.findViewById(R.id.tv_bathroom);
			holder.tv_stall = (TextView)convertView.findViewById(R.id.tv_stall);
			holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
			holder.tv_monthly_rent = (TextView)convertView.findViewById(R.id.tv_monthly_rent);
			holder.tv_return_rate = (TextView)convertView.findViewById(R.id.tv_return_rate);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		EarningModel mEarningModel = (EarningModel) datas.get(position);
		// 这里写死了 集合中只有一个对象
		int myPosition = position >= 1 ? position - 1 : 0;
		EarningDetail mLbEarningModel = earningDetailLists.get(myPosition);
		holder.tv_address.setText(mLbEarningModel.room_country+ "\n"
				+ mLbEarningModel.room_address);
		holder.tv_rent_state.setText(mLbEarningModel.room_state);
		holder.tv_squar.setText(mLbEarningModel.room_acreage);
		holder.tv_bedroom.setText(mLbEarningModel.room_bedroom);
		holder.tv_bathroom.setText(mLbEarningModel.room_bath);
		holder.tv_stall.setText(mLbEarningModel.room_berth);
		holder.tv_price.setText(mLbEarningModel.room_pin);
		holder.tv_monthly_rent.setText(mLbEarningModel.room_rent);
		holder.tv_return_rate.setText(mLbEarningModel.room_return);
		
			
		
		if (mEarningModel != null && holder != null) {
			String url = LBUrls.ALL_IMG_PRE_URL + mLbEarningModel.room_img;
			// String url =
			// "http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg";
			ImageLoaderHelper.displayImage(url, holder.img_houses);
			holder.lly_add.setVisibility(View.GONE);
			holder.lly_main.setVisibility(View.VISIBLE);
			if (position == 1) {
				holder.lly_add.setVisibility(View.VISIBLE);
				holder.lly_main.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	/**
	 * 从新组装String
	 * 
	 * @Title: getSpannaleText
	 * @Description: TODO
	 * @param @param str
	 * @param @return
	 * @return SpannableString
	 * @throws
	 */
	private SpannableString getSpannaleText(String str) {
		SpannableString spanText = new SpannableString(str);
		spanText.setSpan(new ForegroundColorSpan(mContext.getResources()
				.getColor(R.color.text_gray)), 0, str.length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		return spanText;
	}

	private class ViewHolder {
		public TextView tv_address;
		public ImageView img_houses;
		private LinearLayout lly_add, lly_main;
		
		public TextView tv_rent_state;
		public TextView tv_squar;
		public TextView tv_bedroom;
		public TextView tv_bathroom;
		public TextView tv_stall;
		
		public TextView tv_price;
		public TextView tv_monthly_rent;
		public TextView tv_return_rate;
		
	}

}
