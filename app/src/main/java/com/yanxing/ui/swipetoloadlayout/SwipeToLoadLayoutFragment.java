package com.yanxing.ui.swipetoloadlayout;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * SwipeToLoadLayout
 * Created by 李双祥 on 2017/4/9.
 */
public class SwipeToLoadLayoutFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener{

    @BindView(R.id.swipe_to_load_layout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;

    private List<Integer> mList=new ArrayList<>();
    private RecyclerViewAdapter<Integer> mIntegerRecyclerViewAdapter;
    private int mIndex=4;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_swipe_to_load_layout;
    }

    @Override
    protected void afterInstanceView() {
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (recyclerView==null){
                        showToast("为空");
                    }
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onLoadMore() {
        for (int i=mIndex;i<(mIndex+4);i++){
            mList.add(i);
        }
        mIndex=mIndex+4;
        mIntegerRecyclerViewAdapter.update(mList);
    }

    @Override
    public void onRefresh() {
        mList.add(1);
        mList.add(2);
        mList.add(3);
        mIntegerRecyclerViewAdapter=new RecyclerViewAdapter<Integer>(mList
                ,R.layout.adapter_recyclerview_load_more) {
            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.setText(R.id.text,String.valueOf(mList.get(position)));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mIntegerRecyclerViewAdapter);
        mSwipeToLoadLayout.setRefreshing(false);
    }
}
