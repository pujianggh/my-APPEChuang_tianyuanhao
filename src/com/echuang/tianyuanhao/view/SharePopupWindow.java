package com.echuang.tianyuanhao.view;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.activity.CollectActivity;
import com.echuang.tianyuanhao.api.Parameter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class SharePopupWindow extends PopupWindow implements OnClickListener {
	public RelativeLayout conentView;
	private ImageView ivCollect;
	private ImageView ivShare;
	protected Context context;
	UMImage image = new UMImage(context,
			"http://www.umeng.com/images/pic/social/integrated_3.png");
	private UMShareListener umShareListener;

	public SharePopupWindow(final Context mContext) {
		context = mContext;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = (RelativeLayout) inflater.inflate(
				R.layout.share_popupwindow, null);
		int h = ((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getHeight();
		int w = ((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(conentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		// this.setWidth(w / 2 + 50);
		this.setWidth(w / 3);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		// this.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimationPreview);

		initView();

	}

	private void initView() {
		
		
		
		
		
		
		
		
		
		
		
		
		

		umShareListener = new UMShareListener() {
			@Override
			public void onResult(SHARE_MEDIA platform) {
				Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onError(SHARE_MEDIA platform, Throwable t) {
				Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT)
						.show();
			}
		};

		ivCollect = (ImageView) conentView.findViewById(R.id.iv_collect);
		ivShare = (ImageView) conentView.findViewById(R.id.iv_share);

		ivCollect.setOnClickListener(this);
		ivShare.setOnClickListener(this);
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
		} else {
			this.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_collect:
			Toast.makeText(Parameter.APP, "我点击了搜藏", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, CollectActivity.class);
			context.startActivity(intent);
			break;
		case R.id.iv_share:
			Toast.makeText(Parameter.APP, "我点击了分享", Toast.LENGTH_SHORT).show();
			/*
			 * Intent intentshare = new Intent(context,ShareActivity.class);
			 * context.startActivity(intentshare);
			 */
			showShare();

			break;

		default:
			break;
		}

	}

	private void showShare() {
		final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
				SHARE_MEDIA.SINA };
		//, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.DOUBAN
		
		new ShareAction((Activity) context).setDisplayList(displaylist)
				.withText("呵呵").withTitle("title")
				.withTargetUrl("http://www.baidu.com").withMedia(image)
				.setListenerList(umShareListener).open();
	}

}
