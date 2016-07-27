package com.yanxing.ui;

import android.view.View;
import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * UltraPtr
 * Created by lishuangxiang on 2016/5/30.
 */
public class UltraPtrExampleActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;

    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout mPtrClassicFrameLayout;

    private List<String> mList = new ArrayList<String>();
    private CommonAdapter<String> mCommonAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_ultra_ptr;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true,this);
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load();
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrClassicFrameLayout.setResistance(1.7f);
        mPtrClassicFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrClassicFrameLayout.setDurationToClose(200);
        mPtrClassicFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrClassicFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrClassicFrameLayout.setKeepHeaderWhenRefresh(true);
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
        mCommonAdapter = new CommonAdapter<String>(mList,R.layout.adapter_content) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.name,mList.get(position));
            }
        };
        mListView.setAdapter(mCommonAdapter);
    }
}
