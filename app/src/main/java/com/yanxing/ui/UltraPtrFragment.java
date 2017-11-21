package com.yanxing.ui;

import android.widget.ListView;

import com.yanxing.adapterlibrary.CommonAdapter;
import com.yanxing.adapterlibrary.ViewHolder;
import com.yanxing.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * UltraPtr
 * Created by lishuangxiang on 2016/5/30.
 */
public class UltraPtrFragment extends BaseFragment {

    @BindView(R.id.listview)
    ListView mListView;

    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout mPtrClassicFrameLayout;

    private List<String> mList = new ArrayList<String>();
    private CommonAdapter<String> mCommonAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_ultra_ptr;
    }

    @Override
    protected void afterInstanceView() {
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load();
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
        mPtrClassicFrameLayout.autoRefresh(true);
    }

    //javaScript
    public void load() {
        for (int i=0;i<10;i++){
            mList.add(String.valueOf(i));
        }
        mCommonAdapter = new CommonAdapter<String>(mList,R.layout.adapter_content) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                viewHolder.setText(R.id.name,mDataList.get(position));
            }
        };
        mListView.setAdapter(mCommonAdapter);
    }
}
