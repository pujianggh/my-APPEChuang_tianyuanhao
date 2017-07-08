package com.echuang.tianyuanhao.imageconfig;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 图片显示动画效果类
 * 
 * @ClassName: FadeImageView
 * @Description: TODO 设置bitmap时会显示淡入淡出的动画的ImageView
 * @author 蒲江
 * @date 2016-7-11 上午10:51:08
 */
public class FadeImageView extends ImageView {
	private AlphaAnimation alphaOut;
	private AlphaAnimation alphaIn;

	private Context context;

	public FadeImageView(Context context) {
		super(context);
		this.context = context;
	}

	public FadeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	private AlphaAnimation getAlphaOut() {
		if (null != alphaOut) {
			return alphaOut;
		}
		alphaOut = new AlphaAnimation(1.0f, 0f);
		alphaOut.setFillAfter(true);
		alphaOut.setDuration(200);
		return alphaOut;
	}

	private AlphaAnimation getAlphaIn() {
		if (null != alphaIn) {
			return alphaIn;
		}
		alphaIn = new AlphaAnimation(0f, 1.0f);
		alphaIn.setFillAfter(true);
		alphaIn.setDuration(200);
		return alphaIn;
	}

	public void setImageBitmapAnim(final Bitmap bm) {
		// super.setImageBitmap(bm);
		getAlphaOut().setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setImageDrawable(new BitmapDrawable(context.getResources(), bm));
				startAnimation(getAlphaIn());
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		this.startAnimation(getAlphaOut());

	}

}
