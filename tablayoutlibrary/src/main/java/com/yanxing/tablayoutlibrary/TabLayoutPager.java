package com.yanxing.tablayoutlibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yanxing.tablayoutlibrary.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TabLayout+ViewPager选项卡
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager extends FrameLayout {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
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
            mTabLayout.setBackgroundColor(mTabBackgroundResId);
            ViewCompat.setBackground(mTabLayout, AppCompatResources.getDrawable(context, mTabBackgroundResId));
        } finally {
            a.recycle();
            ta.recycle();
        }


    }

    /**
     * 添加选项卡
     *
     * @param fragments
     * @param tabs      选项卡名称
     */
    public void addTab(FragmentManager fragmentManager, List<Fragment> fragments, List<String> tabs) {
        for (int i = 0; i < tabs.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabs.get(i)));
        }
        TabLayoutPagerAdapter tabLayoutPagerAdapter = new TabLayoutPagerAdapter(fragmentManager, fragments, tabs);
        mViewPager.setAdapter(tabLayoutPagerAdapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(tabLayoutPagerAdapter);
    }

    /**
     * 修改tabLayout下划线宽度，在addTab方法之后设置
     *
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(int leftDip, int rightDip) {
        Class<?> tabLayout = mTabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(mTabLayout);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * @return android.support.design.widget.TabLayout
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * @return
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

}
