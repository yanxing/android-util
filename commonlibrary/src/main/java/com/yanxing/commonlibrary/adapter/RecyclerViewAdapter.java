package com.yanxing.commonlibrary.adapter;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView适配器抽象类
 * Created by lishuangxiang on 2016/3/23.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    protected List<T> mDataList;
    /**
     * Item view
     */
    private int mLayoutID;
    /**
     * 头部
     */
    private View mHeaderView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private static final int ITEM_TYPE_HEADER = 100;


    /**
     * @param dataList
     * @param layoutID 布局文件
     */
    public RecyclerViewAdapter(List<T> dataList, int layoutID) {
        this.mLayoutID = layoutID;
        this.mDataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //头部
        if (viewType == ITEM_TYPE_HEADER) {
            view = mHeaderView;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(mLayoutID, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder, position);
                }
            }
        });
        //长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(holder, position);
                }
                return true;
            }
        });
    }

    /**
     * 添加头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderView = view;
    }

    @Override
    public int getItemCount() {
        return (mDataList == null ? 0 : mDataList.size()) + getHeadersCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return super.getItemViewType(position - getHeadersCount());
    }

    private int getHeadersCount() {
        return mHeaderView != null ? 1 : 0;
    }

    /**
     * 添加指定元素，有动画效果
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        mDataList.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 移除指定元素，有动画效果
     *
     * @param position
     */
    public void remove(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 更新数据，没有动画效果
     */
    public void update(List<T> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     */
    public void update(int position) {
        notifyItemChanged(position);
    }

    /**
     * ViewHolder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }

        /**
         * 通过控件的Id获取控件
         *
         * @param viewID
         * @param <T>
         * @return
         */
        public <T extends View> T findViewById(int viewID) {
            View view = itemView.findViewById(viewID);
            return (T) view;
        }

        /**
         * 为TextView设置字符串
         *
         * @param viewId
         * @param text
         * @return
         */
        public void setText(int viewId, CharSequence text) {
            TextView view = findViewById(viewId);
            view.setText(text);
        }

        /**
         * 为TextView设置颜色
         *
         * @param viewId
         * @param color
         * @return
         */
        public void setTextColor(int viewId, int color) {
            TextView view = findViewById(viewId);
            view.setTextColor(color);
        }

        /**
         * 设置背景
         *
         * @param viewId
         * @param resid
         */
        public void setBackgroundResource(int viewId, @DrawableRes int resid) {
            findViewById(viewId).setBackgroundResource(resid);
        }
    }

    /**
     * 设置Item点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClick(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置Item长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }


    public interface OnItemClickListener {
        /**
         * item点击事件
         *
         * @param viewHolder
         * @param position
         */
        void onItemClick(MyViewHolder viewHolder, int position);
    }

    public interface OnItemLongClickListener {
        /**
         * item长按事件
         *
         * @param viewHolder
         * @param position
         * @return
         */
        boolean onItemLongClick(MyViewHolder viewHolder, int position);
    }

}

