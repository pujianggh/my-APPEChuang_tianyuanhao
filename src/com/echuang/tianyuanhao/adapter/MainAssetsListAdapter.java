package com.echuang.tianyuanhao.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.echuang.tianyuanhao.activity.PropertyDetailsActivity;
import com.echuang.tianyuanhao.instance.LBInstance;
import com.echuang.tianyuanhao.model.LBMainAssetsModel.Resultdata;
import com.echuang.tianyuanhao.model.MainAssetsModel;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;

/**
 * 首页
 * 
 * @ClassName: MainAssetsListAdapter
 * @Description: TODO
 * @author
 * @date 2016-9-19 下午14:55:03
 */
public class MainAssetsListAdapter extends BaseAdapter {
	private List<MainAssetsModel> datas;
	private Context mContext;
	private ViewHolder holder;
	private List<Resultdata> mList;
	private OnItemShowPopupClick mOnItemShowPopupClick = null;

	public void setData(List<MainAssetsModel> datas) {
		this.datas = datas;
	}

	public MainAssetsListAdapter() {
		super();
	}

	public MainAssetsListAdapter(Context context) {
		super();

	}

	public MainAssetsListAdapter(Context context, List<Resultdata> list) {
		this.mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		return mList.size();
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

		TextView tvTestUp;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_main_assets, null);
			holder.img_houses = (ImageView) convertView
					.findViewById(R.id.img_houses);
			holder.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_monthly_rent = (TextView) convertView
					.findViewById(R.id.tv_monthly_rent);
			holder.tv_return_rate = (TextView) convertView
					.findViewById(R.id.tv_return_rate);
			// 后面添加的
			holder.tv_rent_state = (TextView) convertView
					.findViewById(R.id.tv_rent_state);
			holder.tv_squar = (TextView) convertView
					.findViewById(R.id.tv_squar);
			holder.tv_bedroom = (TextView) convertView
					.findViewById(R.id.tv_bedroom);
			holder.tv_bathroom = (TextView) convertView
					.findViewById(R.id.tv_bathroom);
			holder.tv_stall = (TextView) convertView
					.findViewById(R.id.tv_stall);
			holder.tv_sj = (TextView) convertView.findViewById(R.id.tv_sj);
			holder.tv_yzj = (TextView) convertView.findViewById(R.id.tv_yzj);
			holder.tv_mhbl = (TextView) convertView.findViewById(R.id.tv_mhbl);

			// item是否覆盖不同的销售状态
			holder.ll_home_assets_up = (LinearLayout) convertView
					.findViewById(R.id.ll_home_assets_up);

			// holder.flState = (FrameLayout) convertView
			// .findViewById(R.id.fl_assets_item);
			// holder.tv_what_state = (TextView) convertView
			// .findViewById(R.id.tv_what_state);

			// holder.tv_testup =
			// (TextView)convertView.findViewById(R.id.tv_testup);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		tvTestUp = (TextView) convertView.findViewById(R.id.tv_testup);

		final Resultdata resultdata = mList.get(position);

		if (resultdata != null && holder != null) {
			// String url =
			// "http://img.taopic.com/uploads/allimg/166213/240512-16621669151737.jpg";

			ImageLoaderHelper.displayImage(LBInstance.HOME_IMG_URL
					+ resultdata.room_img, holder.img_houses);
			// Picasso.with(mContext).load(LBInstance.HOME_IMG_URL
			// + resultdata.room_img).into(holder.img_houses);

			holder.tv_address.setText(resultdata.room_address + "\n"
					+ resultdata.room_country);
			holder.tv_price.setText("$" + resultdata.room_pins);
			holder.tv_return_rate.setText(resultdata.room_return);
			holder.tv_monthly_rent.setText("$" + resultdata.room_rents);
			// 后面添加的
			holder.tv_rent_state.setText(resultdata.room_state);
			holder.tv_squar.setText(resultdata.room_acreage);
			holder.tv_bedroom.setText(resultdata.room_bedroom);
			holder.tv_bathroom.setText(resultdata.room_bath);
			holder.tv_stall.setText(resultdata.room_berth);
			int state = Integer.parseInt(resultdata.room_state);

			switch (state) {
			case 1:
				tvTestUp.setText("正常状态");
				tvTestUp.setBackgroundColor(Color.argb(99, 0, 0, 0));
				tvTestUp.setTextColor(Color.argb(99, 244, 174, 58));
				break;
			case 2:
				tvTestUp.setText("租赁中");
				tvTestUp.setBackgroundColor(Color.argb(0, 0, 0, 0));
				tvTestUp.setTextColor(Color.argb(0, 0, 0, 0));
				break;
			case 3:
				tvTestUp.setText("已被预定(空)");
				tvTestUp.setBackgroundColor(Color.argb(99, 0, 0, 0));
				tvTestUp.setTextColor(Color.argb(99, 244, 174, 58));
				break;
			case 4:
				tvTestUp.setText("已被预定(租)");
				tvTestUp.setBackgroundColor(Color.argb(99, 0, 0, 0));
				// tvTestUp.setTextColor(Color.argb(99, 244, 174, 58));
				tvTestUp.setTextColor(mContext.getResources().getColor(
						R.color.pic_text_orange));
				break;
			case 5:
				tvTestUp.setText("已出售(空)");
				tvTestUp.setBackgroundColor(Color.argb(99, 0, 0, 0));
				tvTestUp.setTextColor(Color.argb(99, 244, 174, 58));
				break;
			case 6:
				tvTestUp.setText("已出售(租)");
				tvTestUp.setBackgroundColor(Color.argb(99, 0, 0, 0));
				tvTestUp.setTextColor(Color.argb(99, 244, 174, 58));
				break;

			default:
				tvTestUp.setVisibility(View.INVISIBLE);
				break;
			}

			holder.ll_home_assets_up
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View view) {
							int stateNum = 10000;
							if (resultdata.room_state != null) {
								stateNum = Integer
										.parseInt(resultdata.room_state);
							}

							if (stateNum == 2) {
								Intent intent = new Intent();
								Bundle detailBundle1 = new Bundle();
								// 把rid传到详情页
								detailBundle1.putString("rid", resultdata.rid);
								intent.putExtras(detailBundle1);
								intent.setClass(mContext,
										PropertyDetailsActivity.class);
								mContext.startActivity(intent);
							}
						}
					});

			// 售价
			holder.tv_sj.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (mOnItemShowPopupClick != null) {
						mOnItemShowPopupClick.itemShowPopupClick(view, "展示数据1");
					}
				}
			});
			// 月租金
			holder.tv_yzj.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (mOnItemShowPopupClick != null) {
						mOnItemShowPopupClick.itemShowPopupClick(view, "展示数据2");
					}
				}
			});
			// 毛回报率
			holder.tv_mhbl.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (mOnItemShowPopupClick != null) {
						mOnItemShowPopupClick.itemShowPopupClick(view, "展示数据3");
					}
				}
			});
		}
		return convertView;
	}

	/**
	 * 调取数据层接口
	 * @Title: setItemShowPopupClick
	 * @Description: TODO 
	 * @param @param popup
	 * @return void
	 * @throws
	 */
	public void setItemShowPopupClick(OnItemShowPopupClick popup) {
		mOnItemShowPopupClick = popup;
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
		public TextView tv_price;
		public TextView tv_monthly_rent;
		public TextView tv_return_rate;
		public ImageView img_houses;

		public TextView tv_rent_state;
		public TextView tv_squar;
		public TextView tv_bedroom;
		public TextView tv_bathroom;
		public TextView tv_stall;

		public TextView tv_sj;
		public TextView tv_mhbl;
		public TextView tv_yzj;
		public LinearLayout ll_home_assets_up;

	}
	
	public interface OnItemShowPopupClick {
		public void itemShowPopupClick(View v, String infoString);
	}

}
