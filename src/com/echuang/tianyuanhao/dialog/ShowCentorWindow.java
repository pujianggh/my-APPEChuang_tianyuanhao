package com.echuang.tianyuanhao.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.TopCornerListAdapter;
import com.echuang.tianyuanhao.model.CornerModel;

/**
 * 选择弹窗口
 * @ClassName: ShowTopCentorWindow
 * @Description: TODO
 * @author 蒲江
 * @date 2016-9-1 上午11:00:28
 */
public class ShowCentorWindow extends PopupWindow {
	private Activity mContext;
	private View mMenuView;
	private OnItemSelectedListener itemSelectedListener;
	private ListView lv_content;
	private RelativeLayout root;
	private List<CornerModel> list = new ArrayList<CornerModel>();
	private TopCornerListAdapter adapter;

	public ShowCentorWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ShowCentorWindow(Activity context,
			final List<CornerModel> list,
			final OnItemSelectedListener itemSelectedListener) {
		super(context);
		this.itemSelectedListener = itemSelectedListener;
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_show, null);
		lv_content = (ListView) mMenuView.findViewById(R.id.listView_content);
		lv_content.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new TopCornerListAdapter(mContext);
		adapter.setData(list);
		adapter.setIndex(0);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != itemSelectedListener) {
					itemSelectedListener.OnSelected(position,
							list.get(position));
				}
				adapter.setDataFlag(position);//排序重新设置数据
				adapter.setIndex(position);
				adapter.notifyDataSetChanged();
				ShowCentorWindow.this.dismiss();
			}
		});
		lv_content.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				adapter.setIndex(arg2);
				adapter.notifyDataSetChanged();
				ShowCentorWindow.this.dismiss();
				return false;
			}
			
		});
		root = (RelativeLayout) mMenuView.findViewById(R.id.root);
		root.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 销毁弹出框
				dismiss();
			}
		});
		this.setContentView(mMenuView);
		/**
		 * 添加动画效果
		 */
		//this.setAnimationStyle(R.style.popup_invest_sequence_acimation);
		this.setWidth(300);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(R.drawable.title_function_bg);
		// 设置SelectPicPopupWindow弹出窗体的背景
		dw.setAlpha(0);
		this.setBackgroundDrawable(dw);
	}

	public void setAdapterSelected(boolean flag) {
		if (adapter != null) {
			adapter.setNeedSelected(flag);
			adapter.setIndex(0);
			adapter.notifyDataSetChanged();
		}
	}
	public void setAdapterNeedArrow(boolean flag) {
		if (adapter != null) {
			adapter.setNeedArrow(flag);
			adapter.setIndex(0);
			adapter.notifyDataSetChanged();
		}
	}
	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
	}

	@Override
	public void showAsDropDown(View anchor) {
		super.showAsDropDown(anchor);
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		super.showAsDropDown(anchor, xoff, yoff);
	}

	public ShowCentorWindow setItemSelectedListener(
			OnItemSelectedListener itemSelectedListener) {
		this.itemSelectedListener = itemSelectedListener;
		return this;
	}

	public OnItemSelectedListener getItemSelectedListener() {
		return itemSelectedListener;
	}

	public List<CornerModel> getList() {
		return list;
	}

	public void setList(List<CornerModel> list) {
		this.list = list;
		adapter.setData(list);
		lv_content.setAdapter(adapter);
	}

	public interface OnItemSelectedListener {
		void OnSelected(int position, CornerModel data);
	}
	
}