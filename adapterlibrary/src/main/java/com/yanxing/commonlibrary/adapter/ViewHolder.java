package com.yanxing.commonlibrary.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 缓存convertView
 * Created by lishuangxiang on 2016/1/20.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(ViewGroup parent, int layoutId){
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    /**
     * 得到ViewHolder
     * @param convertView 布局view
     * @param parent
     * @param layoutID 布局文件ID
     * @return
     */
    public static ViewHolder getViewHolder(View convertView,ViewGroup parent,int layoutID){
        if (convertView==null){
            return new ViewHolder(parent,layoutID);
        }else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过控件的Id获取控件
     * @param viewID
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int viewID){
        View view=mViews.get(viewID);
        if (view==null){
            view=mConvertView.findViewById(viewID);
            mViews.put(viewID,view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     * @param viewId
     * @param text
     * @return
     */
    public void setText(int viewId, CharSequence text){
        TextView view = findViewById(viewId);
        view.setText(text);
    }

    /**
     * 为ImageView设置背景图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public void setBackgroundResource(int viewId, int drawableId){
        ImageView view = findViewById(viewId);
        view.setBackgroundResource(drawableId);
    }

    /**
     * 获取布局view
     * @return
     */
    public View getConvertView(){
        return mConvertView;
    }
}
