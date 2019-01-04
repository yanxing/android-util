package com.yanxing.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * RecyclerView上拉加载更多，这里滑动底部时加载数据 未完
 * Created by 李双祥 on 2017/4/4.
 */
public class RecyclerViewLoadMore extends RecyclerView {

    private OnLoadMoreListener mOnLoadMoreListener;

    public RecyclerViewLoadMore(Context context) {
        super(context);
    }

    public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置上拉加载更多监听
     * @param loadMoreListener
     */
    public void addOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
        this.mOnLoadMoreListener=loadMoreListener;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //滑动到底部
        if (computeVerticalScrollExtent() + computeVerticalScrollOffset()
                >= computeVerticalScrollRange()){
            if (mOnLoadMoreListener!=null){
                mOnLoadMoreListener.onLoadMore();
//                Adapter adapter=getAdapter();
//                if (ad)

            }
        }
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

}
