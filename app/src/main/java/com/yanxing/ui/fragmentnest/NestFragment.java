package com.yanxing.ui.fragmentnest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yanxing.base.BaseFragment;
import com.yanxing.ui.R;

import butterknife.OnClick;

/**
 * 嵌套两个Fragment
 * Created by lishuangxiang on 2016/9/23.
 */

public class NestFragment extends BaseFragment {

    //显示AFragment
    private boolean mIsA = true;
    private AFragment mAFragment = new AFragment();
    private BFragment mBFragment = new BFragment();

    FragmentManager fragmentManager;

    @Override
    protected int getLayoutResID() {
        return R.layout.nest_fragment;
    }

    @Override
    protected void afterInstanceView() {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, mAFragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.change)
    public void click() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mIsA) {
            fragmentTransaction.replace(R.id.fragment, mBFragment);
        } else {
            fragmentTransaction.replace(R.id.fragment, mAFragment);
        }
        mIsA = !mIsA;
        fragmentTransaction.commit();
    }
}
