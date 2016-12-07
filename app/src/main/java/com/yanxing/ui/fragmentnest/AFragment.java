package com.yanxing.ui.fragmentnest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;
import com.yanxing.util.LogUtil;

/**
 * Created by lishuangxiang on 2016/9/23.
 */

public class AFragment extends BaseFragment {
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_a;
    }

    @Override
    protected void afterInstanceView() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG,"onActivityCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        LogUtil.d(TAG,"onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d(TAG,"onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        LogUtil.d(TAG,"onDetach");
        super.onDetach();
    }

    @Override
    public void onPause() {
        LogUtil.d(TAG,"onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        LogUtil.d(TAG,"onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        LogUtil.d(TAG,"onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        LogUtil.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }
}
