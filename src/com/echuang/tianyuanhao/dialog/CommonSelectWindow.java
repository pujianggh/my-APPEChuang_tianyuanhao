package com.echuang.tianyuanhao.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.adapter.CommonListAdapter;
import com.echuang.tianyuanhao.model.CornerModel;

/**
 * 通用选择弹窗口
 * 
 * @ClassName: ShowTopCentorWindow
 * @Description: TODO
 * @author 蒲江
 * @date 2016-9-1 上午11:00:28
 */
public class CommonSelectWindow extends AlertDialog {
	private Context mContext;
	private OnItemSelectedListener itemSelectedListener;
	private ListView lv_content;
	private RelativeLayout root;
	private List<CornerModel> list = new ArrayList<CornerModel>();
	private CommonListAdapter adapter;

	public CommonSelectWindow(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_common_select);
		setCanceledOnTouchOutside(true);
		getWindow().setGravity(Gravity.BOTTOM);
		getWindow().setWindowAnimations(R.style.avatar_editor_dialog);
		lv_content = (ListView) findViewById(R.id.listView_content);
		lv_content.setSelector(new ColorDrawable(Color.TRANSPARENT));
		list.clear();
		list.add(new CornerModel("在天元号浏览房产", "1", 0));
		list.add(new CornerModel("添加您已购置的海外房产", "2", 0));
		adapter = new CommonListAdapter(mContext);
		adapter.setData(list);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != itemSelectedListener) {
					itemSelectedListener.OnSelected(position,
							list.get(position));
				}
				adapter.notifyDataSetChanged();
				CommonSelectWindow.this.dismiss();
			}
		});
		lv_content
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						adapter.notifyDataSetChanged();
						CommonSelectWindow.this.dismiss();
						return false;
					}

				});
		root = (RelativeLayout) findViewById(R.id.root);
		root.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// 销毁弹出框
				dismiss();
			}
		});
	}

	public CommonSelectWindow(Activity context,
			final OnItemSelectedListener itemSelectedListener) {
		super(context);
		this.itemSelectedListener = itemSelectedListener;
		mContext = context;
	}

	public void setAdapterSelected(boolean flag) {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public void setAdapterNeedArrow(boolean flag) {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public CommonSelectWindow setItemSelectedListener(
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
		adapter = new CommonListAdapter(mContext);
		adapter.setData(list);
		lv_content.setAdapter(adapter);
	}

	public interface OnItemSelectedListener {
		void OnSelected(int position, CornerModel data);
	}

}