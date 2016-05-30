package com.yanxing.ui;

import android.view.View;

import com.swipelistviewlibrary.view.SwipeListView;
import com.yanxing.adapter.SwipeAdapter;
import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by lishuangxiang on 2016/5/30.
 */
@EActivity(R.layout.activity_ultra_ptr)
public class UltraPtrExampleActivity extends BaseActivity {

    @ViewById(R.id.swipeListView)
    SwipeListView mSwipeListView;

    @ViewById(R.id.store_house_ptr_frame)
    PtrFrameLayout mPtrFrameLayout;

    private List<String> mList = new ArrayList<String>();
    private SwipeAdapter mSwipeAdapter;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mPtrFrameLayout.setPullToRefresh(true);
        StoreHouseHeader header = new StoreHouseHeader(UltraPtrExampleActivity.this);
//        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("Ultra PTR");

        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load();
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    //test
    public void load() {
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mSwipeAdapter = new SwipeAdapter(mList);
        mSwipeListView.setAdapter(mSwipeAdapter);
    }
}
