package com.yanxing.ui.tablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.yanxing.base.BaseFragment;
import com.yanxing.tablayoutlibrary.TabLayoutPager;
import com.yanxing.ui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * tablayoutpagerlibrary例子
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPagerFragment extends BaseFragment {

    @BindView(R.id.tabLayoutPager)
    TabLayoutPager mTabLayoutPager;

    private List<Fragment> mFragmentList=new ArrayList<Fragment>();
    private List<String>  mStringList=new ArrayList<String>();

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tablayoutpager;
    }

    @Override
    protected void afterInstanceView() {
        mFragmentList.add(new TabLayoutPager1Fragment());
        mFragmentList.add(new TabLayoutPager2Fragment());
        mFragmentList.add(new TabLayoutPager3Fragment());
        mStringList.add(getString(R.string.menu1));
        mStringList.add(getString(R.string.menu2));
        mStringList.add(getString(R.string.menu3));
        mTabLayoutPager.addTab(getFragmentManager(),mFragmentList,mStringList);
        mTabLayoutPager.setIndicator(30,30);
        mTabLayoutPager.getTabLayout().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabLayoutPager.getViewPager().setCurrentItem(tab.getPosition());
                showToast(getString(R.string.di)+(tab.getPosition()+1)+getString(R.string.ge));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
