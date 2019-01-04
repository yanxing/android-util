package com.yanxing.ui;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import com.yanxing.base.BaseFragment;

/**
 * @author 李双祥 on 2018/10/15.
 */
public class DesFragment extends BaseFragment {
    @Override
    protected void afterInstanceView() {
        CollapsingToolbarLayout collapsingToolbarLayout=null;
        AppBarLayout.LayoutParams layoutParams=(AppBarLayout.LayoutParams)collapsingToolbarLayout.getLayoutParams();
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED |AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);


    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_design;
    }
}
