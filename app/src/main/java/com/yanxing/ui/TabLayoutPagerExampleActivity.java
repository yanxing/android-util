package com.yanxing.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.yanxing.base.BaseActivity;
import com.yanxing.tablayoutlibrary.TabLayoutPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * tablayoutpagerlibrary例子
 * Created by lishuangxiang on 2016/3/14.
 */
@EActivity(R.layout.activity_tablayoutpager_example)
public class TabLayoutPagerExampleActivity extends BaseActivity {

    @ViewById(R.id.tabLayoutPager)
    TabLayoutPager mTabLayoutPager;

    private List<Fragment> mFragmentList=new ArrayList<Fragment>();
    private List<String>  mStringList=new ArrayList<String>();

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mFragmentList.add(new TabLayoutPager1Fragment_());
        mFragmentList.add(new TabLayoutPager2Fragment_());
        mFragmentList.add(new TabLayoutPager3Fragment_());
        mStringList.add("菜单一");
        mStringList.add("菜单二");
        mStringList.add("菜单三");
        mTabLayoutPager.addTab(mFragmentList,mStringList);
        mTabLayoutPager.getTabLayout().setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabLayoutPager.getViewPager().setCurrentItem(tab.getPosition());
                showToast("第"+(tab.getPosition()+1)+"个");

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
