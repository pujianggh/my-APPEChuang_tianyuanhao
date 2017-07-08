package com.echuang.tianyuanhao.listview;

import com.echuang.tianyuanhao.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @ClassName: TrbPullableListView
 * @Description: TODO
 *               如果不需要下拉刷新直接在canPullDown中返回false，这里的自动加载和下拉刷新没有冲突，通过增加在尾部的footerview实现自动加载
 *               ， 所以在使用中不要再动footerview
 * @author 蒲江
 * @date 2016-7-11 上午11:39:01
 */
public class TrbPullableListView extends ListView implements Pullable {
	public static final int INIT = 0;
	public static final int LOADING = 1;
	private OnLoadListener mOnLoadListener;
	private ImageView mLoadingView;
	private TextView mStateTextView;
	private int state = INIT;
	private boolean canLoad = true;
	private AnimationDrawable mLoadAnim;

	private boolean iscloseload = false;

	public void setIscloseload(boolean iscloseload) {
		this.iscloseload = iscloseload;
		if (iscloseload) {
			mStateTextView.setVisibility(View.GONE);
		} else {
			mStateTextView.setVisibility(View.VISIBLE);
			mLoadingView.setVisibility(View.VISIBLE);
		}
	}

	public TrbPullableListView(Context context) {
		super(context);
		init(context);
	}

	public TrbPullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TrbPullableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more,
				null);
		mLoadingView = (ImageView) view.findViewById(R.id.loading_icon);
		// mLoadingView.setBackgroundResource(R.anim.loading_anim);
		// mLoadAnim = (AnimationDrawable) mLoadingView.getBackground();
		mStateTextView = (TextView) view.findViewById(R.id.loadstate_tv);
		addFooterView(view, null, false);
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
			// 2015-6-5 添加不满足条件情况 hezhengnan [s]
		} else {
			// changeState(INIT);
		}
		// 2015-6-5 添加不满足条件情况 hezhengnan[e]
	}

	private void changeState(int state) {
		this.state = state;
		switch (state) {
		case INIT:
			// mLoadAnim.stop();
			// 2015-6-5 visible改为gone hezhengnan [s]
			mLoadingView.setVisibility(View.GONE);
			// 2015-6-5 visible改为gone hezhengnan [e]
			// mStateTextView.setText(R.string.more);
			mStateTextView.setText(" ");
			break;

		case LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			// mLoadAnim.start();
			mStateTextView.setText(R.string.loading);
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
			return true;
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
		void onLoad(TrbPullableListView pullableListView);
	}
}
