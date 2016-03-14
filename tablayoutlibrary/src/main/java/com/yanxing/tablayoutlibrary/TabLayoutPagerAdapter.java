package com.yanxing.tablayoutlibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * TabLayoutPager适配器
 * Created by 李双祥 on 2016/3/14.
 */
public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentsList;
    private List<String> mTitleList;

    public TabLayoutPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public TabLayoutPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitleList) {
        super(fm);
        this.fragmentsList = fragments;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentsList.get(i);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题
    }
}