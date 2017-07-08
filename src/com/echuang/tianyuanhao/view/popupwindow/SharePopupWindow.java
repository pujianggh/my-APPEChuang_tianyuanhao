package com.echuang.tianyuanhao.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.echuang.tianyuanhao.R;

/**
 * 弹出对话框
 * 
 * @ClassName: SharePopupWindow
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-21 上午11:36:19
 */
public class SharePopupWindow extends PopupWindow {
	private Context mContext;
	public View view;

	public SharePopupWindow(Context ctx) {
		mContext = ctx;
		init();
		initView();
	}

	public SharePopupWindow(Context ctx, String content, String title) {
		mContext = ctx;
		init();
		initView();
	}

	private void init() {
		setOutsideTouchable(true);
		setFocusable(true);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setAnimationStyle(R.style.popupAnimation);
		ColorDrawable dw = new ColorDrawable(0x0c000000);
		setBackgroundDrawable(dw);
	}

	private void initView() {
		view = View.inflate(mContext, R.layout.popup_share, null);
		setContentView(view);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		setBackgroundAlpha(1f);
		((Activity) mContext).getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public void show(View v) {
		showAtLocation(v, Gravity.BOTTOM, 0, 0);
		setBackgroundAlpha(0.7f);
		((Activity) mContext).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public void setBackgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
	}

}
