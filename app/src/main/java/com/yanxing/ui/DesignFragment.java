package com.yanxing.ui;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Design中控件用法
 * Created by lishuangxiang on 2016/11/18.
 */

public class DesignFragment extends BaseFragment {

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter<String> mRecyclerViewAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_design;
    }

    @Override
    protected void afterInstanceView() {
        mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            strings.add(String.valueOf(i));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewAdapter = new RecyclerViewAdapter<String>(strings, R.layout.adapter_recycler_view) {

            @Override
            public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
                holder.setText(R.id.text,mDataList.get(position));
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
