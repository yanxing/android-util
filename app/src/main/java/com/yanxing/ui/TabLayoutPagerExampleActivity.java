package com.yanxing.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.yanxing.base.BaseActivity;
import com.yanxing.tablayoutlibrary.OnTabLayoutPagerListener;
import com.yanxing.tablayoutlibrary.TabLayoutPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

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
        mFragmentList.add(new TabLayoutPagerFragment_());
        mFragmentList.add(new TabLayoutPagerFragment_());
        mFragmentList.add(new TabLayoutPagerFragment_());
        mStringList.add("菜单一");
        mStringList.add("菜单二");
        mStringList.add("菜单三");
        mTabLayoutPager.addTab(mFragmentList,mStringList);
        mTabLayoutPager.setOnTabSelectedListener(new OnTabLayoutPagerListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                if (position==0){
                    EventBus.getDefault().post("fragment一");
                }else if (position==1){
                    EventBus.getDefault().post("fragment二");
                }else if (position==2){
                    EventBus.getDefault().post("fragment三");
                }
            }
        });
    }
}
