package com.echuang.tianyuanhao.view;

/**
 * ViewHolder
 *
 * @author zhaotian
 * @date 2016/2/19
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Holder
 * 
 * @ClassName: ViewHolder
 * @Description: TODO
 * @author ÆÑ½­
 * @date 2016-7-11 ÉÏÎç11:21:33
 */
public class ViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context mContext;

	public ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mPosition = position;
		this.mContext = context;
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	/**
	 * getHolder
	 * 
	 * @Title: getHolder
	 * @Description: TODO
	 * @param @param context
	 * @param @param convertView
	 * @param @param parent
	 * @param @param layoutId
	 * @param @param position
	 * @param @return
	 * @return ViewHolder
	 * @throws
	 */
	public static ViewHolder getHolder(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder hoder = (ViewHolder) convertView.getTag();
			hoder.mPosition = position;
			return hoder;
		}
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 
	 * @Title: getViewById
	 * @Description: TODO
	 * @param @param resId
	 * @param @return
	 * @return T
	 * @throws
	 */
	public <T extends View> T getViewById(int resId) {
		View view = mViews.get(resId);
		if (view == null) {
			view = mConvertView.findViewById(resId);
			mViews.put(resId, view);
		}
		return (T) view;
	}

	/**
	 * 
	 * @Title: setTextView
	 * @Description: TODO
	 * @param @param resId
	 * @param @param text
	 * @param @return
	 * @return ViewHolder
	 * @throws
	 */
	public ViewHolder setTextView(int resId, String text) {
		TextView tv = (TextView) mViews.get(resId);
		if (!text.isEmpty()) {
			tv.setText(text);
		}
		return this;
	}

	public ViewHolder setTextView(int resId, int textId) {
		TextView tv = (TextView) mViews.get(resId);
		if (textId > 0) {
			tv.setText(textId);
		}
		return this;
	}

	/**
	 * 
	 * @Title: setImageView
	 * @Description: TODO
	 * @param @param resId
	 * @param @param picId
	 * @param @return
	 * @return ViewHolder
	 * @throws
	 */
	public ViewHolder setImageView(int resId, int picId) {
		ImageView iv = (ImageView) mViews.get(resId);
		iv.setImageResource(picId);
		return this;
	}
}
