package com.echuang.tianyuanhao.view;

import com.echuang.tianyuanhao.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 数据加载提示
 * 
 * @ClassName: CustomProgressDialog
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午1:37:59
 */
public class CustomProgressDialog extends Dialog {
	@SuppressWarnings("unused")
	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;
	private static TextView tv;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.dialog_progress);
		tv = (TextView) customProgressDialog.findViewById(R.id.message);
		customProgressDialog.setCanceledOnTouchOutside(false);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog
				.findViewById(R.id.spinnerImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getBackground();
		animationDrawable.start();
	}

	public void setMessage(String message) {
		tv.setText(message);
	}
}