package com.echuang.tianyuanhao.base;/**
 * CommonAdapter
 *
 * @author zhaotian
 * @date 2016/2/19
 */

/**
 * Created by zhaotian on 2016/2/19.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

import com.echuang.tianyuanhao.view.ViewHolder;

/**
 * Adapter基本类
 * @ClassName: CommonAdapter
 * @Description: TODO
 * @author 蒲江
 * @date 2016-7-11 上午11:19:45
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int itemResId;

    public CommonAdapter(Context mContext, List<T> mDatas, int itemResId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.itemResId = itemResId;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mDatas.size() > 0 && mDatas != null){
            return mDatas.size();
        }else {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext, convertView, parent, itemResId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    /**
     * 抽象方法
     * @Title: convert
     * @Description: TODO 
     * @param @param holder
     * @param @param t
     * @return void
     * @throws
     */
    public abstract void convert(ViewHolder holder, T t);
}
