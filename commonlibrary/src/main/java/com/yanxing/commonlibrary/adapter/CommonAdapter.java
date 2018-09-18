package com.yanxing.commonlibrary.adapter;

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

    /**
     * @param dataList 数据集合
     * @param layoutId 布局文件
     */
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

    /**
     * 更新数据
     * @param dataList
     */
    public void update(List<T> dataList){
        this.mDataList=dataList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder = ViewHolder.getViewHolder(convertView,parent,mLayoutId);
        onBindViewHolder(viewHolder, position);
        return viewHolder.getConvertView();
    }

    /**
     * 设置数据
     * @param viewHolder
     * @param position 当前位置的索引
     */
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);
}
