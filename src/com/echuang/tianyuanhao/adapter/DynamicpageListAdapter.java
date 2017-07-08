package com.echuang.tianyuanhao.adapter;

import java.util.List;

import android.R.string;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.instance.LBUrls;
import com.echuang.tianyuanhao.model.DynamicModel;
import com.echuang.tianyuanhao.model.LBDynamic.DynamicItem;
import com.echuang.tianyuanhao.utils.ImageLoaderHelper;

/**
 * 动态适配器
 * 
 * @ClassName: DynamicpageListAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-8-26 上午11:31:38
 */
public class DynamicpageListAdapter extends BaseAdapter {
	private List<DynamicModel> datas;
	private Context mContext;
	private List<DynamicItem> mResultList;

	public void setData(List<DynamicModel> datas) {

	}

	public void setData(List<DynamicModel> datas2, List<DynamicItem> resultList) {
		this.datas = datas2;
		mResultList = resultList;

	}

	public DynamicpageListAdapter(Context context) {
		super();
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (mResultList == null) {
			return 0;
		}
//		return 5;
		 return mResultList.size();

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

		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_dynamic_page, null);
			holder.img_houses = (ImageView) convertView
					.findViewById(R.id.img_houses);
			holder.tv_info = (TextView) convertView.findViewById(R.id.tv_info);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DynamicItem mDynamicModel = (DynamicItem) mResultList.get(position);
		holder.tv_info.setText(mDynamicModel.d_title);
		holder.tv_time.setText(mDynamicModel.d_time);
		if (mDynamicModel != null && holder != null) {
			String lburl = LBUrls.ALL_IMG_PRE_URL + mDynamicModel.d_img;
			// String url =
			// "http://img.taopic.com/uploads/allimg/130213/240512-13021309151737.jpg";
			ImageLoaderHelper.displayImage(lburl, holder.img_houses);
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
		public TextView tv_info, tv_time;
		public ImageView img_houses;
	}

}
