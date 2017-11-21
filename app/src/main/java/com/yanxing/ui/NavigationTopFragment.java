package com.yanxing.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.yanxing.adapterlibrary.RecyclerViewAdapter;
import com.yanxing.base.BaseFragment;
import com.yanxing.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 类似今日头条导航菜单效果
 * Created by 李双祥 on 2017/4/25.
 */
public class NavigationTopFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.recyclerView)
    RecyclerView mNavList;

    private LinearLayoutManager mLinearManager;
    private int mWidth = 1080;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_navigation_top;
    }

    @Override
    protected void afterInstanceView() {
        DisplayMetrics displayMetrics = CommonUtil.getDisplayMetrics(getActivity());
        if (displayMetrics != null) {
            mWidth = displayMetrics.widthPixels;
        }
        mLinearManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.HORIZONTAL, false);
        mNavList.setLayoutManager(mLinearManager);

        //javaScript
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        mNavList.setAdapter(new RecyclerViewAdapter<Integer>(list, R.layout.adapter_nav) {
            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.setText(R.id.menu, "菜单" + list.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int firstItem = mLinearManager.findFirstVisibleItemPosition();
                        //此项距离左边距离
                        int left = mNavList.getChildAt(position - firstItem).getLeft();
                        if (left >= mWidth / 2) {
                            mNavList.smoothScrollBy(holder.itemView.getWidth(), 0);
                        }else {
                            mNavList.smoothScrollBy(-holder.itemView.getWidth(), 0);
                        }
                    }
                });
            }
        });
    }
}
