package com.yanxing.ui;

import com.yanxing.adapter.RecyclerViewLoadMoreAdapter;
import com.yanxing.base.BaseFragment;
import com.yanxing.view.RecyclerViewLoadMore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * RecyclerviewLoadMore
 * Created by 李双祥 on 2017/4/9.
 */
public class RecyclerviewLoadMoreFragment extends BaseFragment {

    @BindView(R.id.recyclerViewLoadMore)
    RecyclerViewLoadMore mRecyclerViewLoadMore;

    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;

    private List<Integer> mList=new ArrayList<>();
    private RecyclerViewLoadMoreAdapter<Integer> mIntegerRecyclerViewLoadMoreAdapter;
    private int mIndex=4;


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_recyclerview_load_more;
    }

    @Override
    protected void afterInstanceView() {
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mList.add(1);
                mList.add(2);
                mList.add(3);
                mIntegerRecyclerViewLoadMoreAdapter=new RecyclerViewLoadMoreAdapter<Integer>(mList
                        ,R.layout.adapter_recyclerview_load_more) {
                    @Override
                    public void onBindViewHolder(MyViewHolder holder, int position) {
                        holder.setText(R.id.text,String.valueOf(mList.get(position)));
                    }
                };
                mRecyclerViewLoadMore.setAdapter(mIntegerRecyclerViewLoadMoreAdapter);
                mPtrFrameLayout.refreshComplete();
            }
        });
        mPtrFrameLayout.autoRefresh(true);

        /////////////////////下拉刷新
        mRecyclerViewLoadMore.addOnLoadMoreListener(new RecyclerViewLoadMore.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    public void loadMore(){
        for (int i=mIndex;i<(mIndex+4);i++){
            mList.add(i);
        }
        mIndex=mIndex+4;
    }
}
