package com.yanxing.ui.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.ui.R;

/**
 * 对SwipeToLoadLayout一些操作封装
 * Created by 李双祥 on 2017/4/10.
 */
public class SwipeToLoadLayout extends com.aspsine.swipetoloadlayout.SwipeToLoadLayout implements PullToRefresh {

    public SwipeToLoadLayout(Context context) {
        this(context,null);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 完成刷新，包括下拉刷新和加载更多
     */
    @Override
    public void refreshComplete(){
        if (isRefreshing()) {
            setRefreshing(false);
        }
        if (isLoadingMore()) {
            setLoadingMore(false);
        }
    }

    /**
     * 设置刷新头尾部
     * @param context
     */
    public void initSwipeToLoadLayout(Context context) {
        View refreshHeaderView = LayoutInflater.from(context).inflate(R.layout.refresh_header_view,
                this, false);
        View refreshFootView = LayoutInflater.from(context).inflate(R.layout.refresh_more_footer_view,
                this, false);
        setLoadMoreFooterView(refreshFootView);
        setRefreshHeaderView(refreshHeaderView);
        setRefreshEnabled(true);
        setLoadMoreEnabled(true);
    }

}
