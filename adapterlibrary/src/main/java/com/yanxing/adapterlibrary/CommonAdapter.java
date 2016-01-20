package com.yanxing.adapterlibrary;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用适配器
 * Created by lishuangxiang on 2016/1/20.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;
    protected int mLayoutId;

    public CommonAdapter(List<T> dataList, int layoutId)
    {
        this.mDataList = dataList;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder = ViewHolder.getViewHolder(convertView,parent,mLayoutId,position);
        onBindViewHolder(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    /**
     * 设置数据
     * @param viewHolder
     * @param item
     */
    public abstract void onBindViewHolder(ViewHolder viewHolder, T item);
}
