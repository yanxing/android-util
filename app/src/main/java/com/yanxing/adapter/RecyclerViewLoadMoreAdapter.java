package com.yanxing.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanxing.ui.R;

import java.util.List;

/**
 * RecyclerView适配器，滑动到底部显示加载更多界面  未完
 * Created by 李双祥 on 2017/3/30.
 */
public abstract class RecyclerViewLoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerViewLoadMoreAdapter.MyViewHolder> {

    protected List<T> mDataList;
    protected int mLayoutID;
    protected boolean mLoadMore=false;
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    private View mViewFoot;

    public RecyclerViewLoadMoreAdapter(List<T> dataList, int layoutID) {
        this.mDataList = dataList;
        this.mLayoutID=layoutID;
    }


    @Override
    public RecyclerViewLoadMoreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_load_more, parent, false);
            mViewFoot=view;
            return new MyViewHolder(mViewFoot);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutID, parent, false);
            return new MyViewHolder(view);
        }

    }

    /**
     * notifyDataSetChanged更新
     * @param dataList
     */
    public void update(List<T> dataList){
        mDataList=dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mLoadMore&&position == getItemCount() - 1) {//加载更多
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    /**
     * 加载更多
     * @param refresh
     */
    public void setAutoLoadMore(boolean refresh){
//        if (mli)
        mLoadMore=refresh;
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyItemInserted(getItemCount() - 1);
            }
        };

        handler.post(r);

    }

    /**
     * 加载完成
     */
    public void refreshComplete(){
//        Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//                notifyItemRemoved(getItemCount() - 1);
//            }
//        };
//
//        handler.post(r);
//        notifyItemRangeRemoved(getItemCount()-1,1);
        mLoadMore=false;
    }

    /**
     * 显示没有更多数据了
     */
    public void showNoData(){

    }


    @Override
    public int getItemCount() {
        int count = (mDataList == null ? 0 : mDataList.size());
        if (mLoadMore){
            count++;
        }
        return count;
    }

    /**
     * 是尾部
     * @param position
     * @return
     */
    public boolean isFooterView(int position) {
        return mViewFoot!=null && position == getItemCount() - 1;
    }

    /**
     * 有尾部
     * @return
     */
    public boolean haveFooterView() {
        return mViewFoot != null;
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
    }
}
