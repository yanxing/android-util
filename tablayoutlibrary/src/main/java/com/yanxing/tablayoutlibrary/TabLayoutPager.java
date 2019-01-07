package com.yanxing.tablayoutlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.ViewCompat;
import androidx.appcompat.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.lang.reflect.Field;
import java.util.List;

/**
 * TabLayout+ViewPager选项卡
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager extends FrameLayout {

    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private int mTabBackgroundResId;

    public TabLayoutPager(Context context) {
        this(context, null);
    }

    public TabLayoutPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.tablayout, this);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutPager);
        int tabTextAppearance = a.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        final TypedArray ta = context.obtainStyledAttributes(tabTextAppearance, R.styleable.TextAppearance);
        try {
            //指示器颜色
            mTabLayout.setSelectedTabIndicatorColor(a.getColor(R.styleable.TabLayoutPager_tabLayoutIndicatorColor, getResources().getColor(R.color.tablayout_black)));

            //选项卡文字颜色
            final int noSelected = a.getColor(R.styleable.TabLayoutPager_tabLayoutTextColor, getResources().getColor(R.color.tablayout_colorGray));
            final int selected = a.getColor(R.styleable.TabLayoutPager_tabLayoutSelectedTextColor, getResources().getColor(R.color.tablayout_black));
            mTabLayout.setTabTextColors(noSelected, selected);
            mTabBackgroundResId = a.getResourceId(R.styleable.TabLayoutPager_tabLayoutBackground, getResources().getColor(R.color.tablayout_white));
            mTabLayout.setBackgroundResource(mTabBackgroundResId);
            if (a.hasValue(R.styleable.TabLayoutPager_tabLayoutHigh)){
                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mTabLayout.getLayoutParams();
                params.height= (int) a.getDimension(R.styleable.TabLayoutPager_tabLayoutHigh,40.f);
            }
        } finally {
            a.recycle();
            ta.recycle();
        }
    }

    /**
     * @param scroll true Viewpager可以滑动，false不可以滑动，默认可以
     */
    public void setViewPagerScroll(boolean scroll) {
        mViewPager.setScroll(scroll);
    }

    /**
     * 添加选项卡
     *
     * @param fragments
     * @param tabs      选项卡名称
     */
    public void addTab(FragmentManager fragmentManager, List<Fragment> fragments, List<String> tabs) {
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            mTabLayout.addTab(tab.setText(tabs.get(i)));
        }
        TabLayoutPagerAdapter tabLayoutPagerAdapter = new TabLayoutPagerAdapter(fragmentManager, fragments, tabs);
        mViewPager.setAdapter(tabLayoutPagerAdapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(tabLayoutPagerAdapter);
    }

    /**
     * @return android.support.design.widget.TabLayout
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public void setTabIndicatorFullWidth(boolean tabIndicatorFullWidth){
        getTabLayout().setTabIndicatorFullWidth(tabIndicatorFullWidth);
    }

    /**
     * @return
     */
    public NoScrollViewPager getViewPager() {
        return mViewPager;
    }

}
