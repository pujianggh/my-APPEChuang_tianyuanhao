
package com.echuang.tianyuanhao.swipebackbase;

import com.echuang.tianyuanhao.R;
import com.echuang.tianyuanhao.swipebackutils.SwipeBackLayout;
import com.echuang.tianyuanhao.swipebackutils.SwipeBackUtils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 管理类
 * @ClassName: SwipeBackActivityHelper
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午1:52:52
 */
public class SwipeBackActivityHelper {
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
    }

    public void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.layout_swipeback, null);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            	if (state == SwipeBackLayout.STATE_IDLE && scrollPercent == 0) {
                    SwipeBackUtils.convertActivityFromTranslucent(mActivity);
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                SwipeBackUtils.convertActivityToTranslucent(mActivity);
            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
        SwipeBackUtils.convertActivityFromTranslucent(mActivity);
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }
}
