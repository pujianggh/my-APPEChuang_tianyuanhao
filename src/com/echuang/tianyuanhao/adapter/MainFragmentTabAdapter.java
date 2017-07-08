package com.echuang.tianyuanhao.adapter;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

/**
 * 主页切换适配器
 * 
 * @ClassName: MainFragmentTabAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 下午5:31:52
 */
public class MainFragmentTabAdapter implements
		RadioGroup.OnCheckedChangeListener {
	private List<Fragment> fragments; // 一个tab页面对应一个Fragment
	private RadioGroup rgs; // 用于切换tab
	private FragmentActivity fragmentActivity; // Fragment所属的Activity
	private int fragmentContentId; // Activity中所要被替换的区域的id
	private Fragment fragment;
	private int currentTab = 0; // 当前Tab页面索引

	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

	public MainFragmentTabAdapter(FragmentActivity fragmentActivity,
			List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.fragmentActivity = fragmentActivity;
		this.fragmentContentId = fragmentContentId;

		if (!fragments.get(0).isAdded() && !fragments.get(0).isRemoving()
				&& !fragments.get(0).isVisible()) {
			// 默认显示第一页
			FragmentTransaction ft = fragmentActivity
					.getSupportFragmentManager().beginTransaction();
			ft.add(fragmentContentId, fragments.get(0));
			ft.commitAllowingStateLoss();
		}
		rgs.setOnCheckedChangeListener(this);
	}

	public MainFragmentTabAdapter(Fragment fragment, List<Fragment> fragments,
			int fragmentContentId, RadioGroup rgs) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.fragment = fragment;
		this.fragmentContentId = fragmentContentId;
		if (!fragments.get(0).isAdded() && !fragments.get(0).isRemoving()
				&& !fragments.get(0).isVisible()) {
			// 默认显示第一页
			FragmentTransaction ft = fragment.getChildFragmentManager()
					.beginTransaction();
			ft.add(fragmentContentId, fragments.get(0));
			ft.commitAllowingStateLoss();
		}

		rgs.setOnCheckedChangeListener(this);

	}

	/**
	 * 切换事件
	 * <p>
	 * Title: onCheckedChanged
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param radioGroup
	 * @param checkedId
	 * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android.widget.RadioGroup,
	 *      int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		// 如果设置了切换tab额外功能功能接口
		for (int i = 0; i < rgs.getChildCount(); i++) {
			if (rgs.getChildAt(i).getId() == checkedId) {
				if (null != onRgsExtraCheckedChangedListener) {
					onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(
							radioGroup, checkedId, i, currentTab);
				}
				// if (checkedId == rgs.getChildAt(rgs.getChildCount() - 1)
				// .getId()
				// && !SharedPreUtils.getInstance(fragmentActivity)
				// .getIsLogin()) {
				// return;
				// }
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);

				getCurrentFragment().onPause(); // 暂停当前tab
				// getCurrentFragment().onStop(); // 暂停当前tab

				if (fragment.isAdded()) {
					// fragment.onStart(); // 启动目标tab的onStart()
					fragment.onResume(); // 启动目标tab的onResume()
				} else {
					ft.add(fragmentContentId, fragment);
				}
				showTab(i); // 显示目标tab
				ft.commitAllowingStateLoss();

			}
		}

	}

	/**
	 * 切换Tab显示隐藏
	 * 
	 * @Title: showTab
	 * @Description: TODO
	 * @param @param idx
	 * @return void
	 * @throws
	 */
	private void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commitAllowingStateLoss();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 切换时候动画效果
	 * 
	 * @Title: obtainFragmentTransaction
	 * @Description: TODO
	 * @param @param index
	 * @param @return
	 * @return FragmentTransaction
	 * @throws
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft;
		if (fragmentActivity != null) {
			ft = fragmentActivity.getSupportFragmentManager()
					.beginTransaction();
		} else {
			ft = fragment.getChildFragmentManager().beginTransaction();

		}
		// 设置切换动画
		// if(index > currentTab){
		// ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
		// }else{
		// ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
		// }
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(
			OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}

	/**
	 * 切换tab额外功能功能接口
	 * 
	 * @ClassName: OnRgsExtraCheckedChangedListener
	 * @Description: TODO
	 * @author 蒲江
	 * @date 2016-7-13 上午11:29:15
	 */
	public static class OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
				int checkedId, int index, int currentTab) {

		}
	}

}
