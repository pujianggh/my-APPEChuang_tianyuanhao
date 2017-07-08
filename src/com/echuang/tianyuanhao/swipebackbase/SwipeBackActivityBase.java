package com.echuang.tianyuanhao.swipebackbase;

import com.echuang.tianyuanhao.swipebackutils.SwipeBackLayout;

/**
 * SwipeBackActivityBase接口
 * @ClassName: SwipeBackActivityBase
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-8 下午1:51:59
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();

}
