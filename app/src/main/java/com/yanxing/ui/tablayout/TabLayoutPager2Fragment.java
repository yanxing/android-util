package com.yanxing.ui.tablayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.EventBusUtil;
import com.yanxing.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;


/**
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager2Fragment extends BaseFragment {


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager2;
    }

    @Override
    protected void afterInstanceView() {
        EventBusUtil.getDefault().register(this);

    }

    @Subscribe
    public void onEvent(String content){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onDestroy");
        EventBusUtil.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtil.d(getTAG(),"TabLayoutPager2Fragment setUserVisibleHint="+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }
}
