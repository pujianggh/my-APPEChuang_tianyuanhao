package com.echuang.tianyuanhao.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.utils.Utils;

/**
 * 提示对话框
 * 
 * @ClassName: TipsDialog
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-21 下午4:22:33
 */
public class TipsDialog extends AlertDialog {
	private Context mContext;
	private TextView tv1, tv2;
	private View.OnClickListener mOKClickListener;
	private TextView tv_title, tv_content;
	private String title, content, button1, button2;

	public TipsDialog(Context context, String title, String content,
			String button1, String button2, View.OnClickListener OKClickListener) {
		super(context);
		this.mContext = context;
		this.mOKClickListener = OKClickListener;
		this.title = title;
		this.content = content;
		this.button1 = button1;
		this.button2 = button2;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tips);
		getWindow().setLayout(Utils.dip2px(mContext, 300f),
				Utils.dip2px(mContext, 190f));
		
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_title.setText(title);
		tv_content.setText(content);
		tv1.setText(button1);
		tv2.setText(button2);

		tv1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		tv2.setOnClickListener(mOKClickListener);
	}
}
