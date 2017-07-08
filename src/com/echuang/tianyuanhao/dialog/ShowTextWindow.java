package com.echuang.tianyuanhao.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.utils.LogAPP;

/**
 * 选择弹窗口
 * 
 * @ClassName: ShowTextWindow
 * @Description: TODO
 * @author 蒲江
 * @date 2016-10-16 下午1:45:58
 */
public class ShowTextWindow extends PopupWindow {
	private Context mContext;
	private View mMenuView;
	private TextView tv_info;
	private RelativeLayout root;

	public ShowTextWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ShowTextWindow(Context context,String infoString) {
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_show_text, null);
		tv_info = (TextView) mMenuView.findViewById(R.id.tv_info);
		tv_info.setText(infoString+"");
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
		// this.setAnimationStyle(R.style.popup_invest_sequence_acimation);
		this.setWidth(300);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(R.drawable.title_function_bg2);
		// 设置SelectPicPopupWindow弹出窗体的背景
		dw.setAlpha(0);
		this.setBackgroundDrawable(dw);
	}
	
	/**
	 * 设置信息
	 * @Title: setInfoString
	 * @Description: TODO 
	 * @param 
	 * @return void
	 * @throws
	 */
	public void setInfoString(String infoString) {
		if (tv_info!=null) {
			tv_info.setText(infoString);
		}
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
	}

	public void showAsDropDown(View v) {
		super.showAsDropDown(v);
		int[] location = new int[2];  
		v.getLocationOnScreen(location);  
		LogAPP.i("pj", "@###---------->"+location[0]);
		LogAPP.i("pj", "@###---------->"+location[1]);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-v.getHeight()-500); 
        
//		int[] location = new int[2];
//		parent.getLocationOnScreen(location);
//		int x = location[0];
//		int y = location[1];
//		x = x - getWidth() / 2;
//		y = y - getHeight() / 4;
//		int popupWidth = mMenuView.getMeasuredWidth();
//	    int popupHeight =  mMenuView.getMeasuredHeight();
//	    showAtLocation(parent, Gravity.NO_GRAVITY, (location[0]+parent.getWidth()/2)-popupWidth/2,
//        		location[1]-popupHeight);
		//showAtLocation(parent, Gravity.TOP | Gravity.LEFT, x, y);
//		showAtLocation(parent, Gravity.NO_GRAVITY, (location[0]+parent.getWidth()/2)-popupWidth/2,
//	        		location[1]-popupHeight);
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		super.showAsDropDown(anchor, xoff, yoff);
	}
}