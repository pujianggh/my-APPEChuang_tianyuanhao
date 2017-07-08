package com.echuang.tianyuanhao.listview;

import com.echuang.tianyuanhao.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 如果不需要下拉刷新直接在canPullDown中返回false
 * 这里的自动加载和下拉刷新没有冲突
 * 通过增加在尾部的footerview实现自动加载，
 * @ClassName: PullableGridView
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-14 下午2:54:25
 */
public class PullableGridView extends GridView implements Pullable {
	public static final int INIT = 0;
	public static final int LOADING = 1;
	private OnLoadListener mOnLoadListener;
	private int state = INIT;
	private boolean canLoad = true;
	private AnimationDrawable mLoadAnim;
	private ImageView mLoadingView;
	private TextView mStateTextView;

	private boolean iscloseload = false;
	
	public void setIscloseload(boolean iscloseload) {
		this.iscloseload = iscloseload;
	}

	public PullableGridView(Context context) {
		super(context);
		init(context);
	}

	public PullableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more,
				null);
		mLoadingView = (ImageView) view.findViewById(R.id.loading_icon);
		mStateTextView = (TextView) view.findViewById(R.id.loadstate_tv);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			// 按下的时候禁止自动加载
			canLoad = false;
			break;
		case MotionEvent.ACTION_UP:
			// 松开手判断是否自动加载
			canLoad = true;
			checkLoad();
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		// 在滚动中判断是否满足自动加载条件
		checkLoad();
	}

	/**
	 * 判断是否满足自动加载条件
	 */
	private void checkLoad() {
		if (reachBottom() && mOnLoadListener != null && state != LOADING
				&& canLoad && !iscloseload) {
			mOnLoadListener.onLoad(this);
			changeState(LOADING);
		} else {
		}
	}

	private void changeState(int state) {
		this.state = state;
		switch (state) {
		case INIT:
			break;

		case LOADING:
			break;
		}
	}

	/**
	 * 完成加载
	 */
	public void finishLoading() {
		changeState(INIT);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	public void setOnLoadListener(OnLoadListener listener) {
		this.mOnLoadListener = listener;
	}

	/**
	 * @return footerview可见时返回true，否则返回false
	 */
	public boolean reachBottom() {
		if (getCount() == 0) {
			// 没有item的时候也可以上拉加载
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// 滑到底部了
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getTop() < getMeasuredHeight())
				return true;
		}
		return false;
	}

	public interface OnLoadListener {
		void onLoad(PullableGridView pullableListView);
	}
}
