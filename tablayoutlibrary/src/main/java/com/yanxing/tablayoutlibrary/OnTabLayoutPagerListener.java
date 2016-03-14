package com.yanxing.tablayoutlibrary;

import android.support.design.widget.TabLayout;

/**
 * TabLayoutPager选择事件
 * Created by lishuangxiang on 2016/3/14.
 */
public abstract class OnTabLayoutPagerListener implements TabLayout.OnTabSelectedListener {

    /**
     * called when tab is selected
     *
     * @param tab
     */
    public abstract void onTabSelected(TabLayout.Tab tab);

    //默认实现
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    //默认实现
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
