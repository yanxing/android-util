package com.yanxing.tablayoutlibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.yanxing.tablayoutlibrary.R;

import java.util.List;

/**
 * TabLayout+ViewPager选项卡
 * Created by lishuangxiang on 2016/3/14.
 */
public class TabLayoutPager extends FrameLayout{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabLayoutPagerAdapter mTabLayoutPagerAdapter;
    private FragmentActivity mActivity;
    private ColorStateList mTabTextColors;

    public TabLayoutPager(Context context) {
        this(context, null);
    }

    public TabLayoutPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.tablayout, this);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mActivity = (FragmentActivity) context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutPager);
        int tabTextAppearance = a.getResourceId(R.styleable.TabLayout_tabTextAppearance,
                R.style.TextAppearance_Design_Tab);
        final TypedArray ta = context.obtainStyledAttributes(tabTextAppearance,
                R.styleable.TextAppearance);
        try {
            mTabTextColors = ta.getColorStateList(R.styleable.TextAppearance_android_textColor);
        } finally {
            ta.recycle();
        }
        //指示器颜色
        if (a.hasValue(R.styleable.TabLayoutPager_tabLayoutIndicatorColor)) {
            mTabLayout.setSelectedTabIndicatorColor(a.getColor(R.styleable.TabLayoutPager_tabLayoutIndicatorColor, 0));
        }
        //选中选项卡文字颜色
        if (a.hasValue(R.styleable.TabLayoutPager_tabLayoutSelectedTextColor)) {
            final int selected = a.getColor(R.styleable.TabLayoutPager_tabLayoutSelectedTextColor, 0);
            mTabLayout.setTabTextColors(mTabTextColors.getDefaultColor(), selected);
        }
        //选项卡文字颜色
        if (a.hasValue(R.styleable.TabLayoutPager_tabLayoutTextColor)) {
            final int noSelected = a.getColor(R.styleable.TabLayoutPager_tabLayoutTextColor, 0);
            final int selected = a.hasValue(R.styleable.TabLayoutPager_tabLayoutSelectedTextColor)
                    ? a.getColor(R.styleable.TabLayoutPager_tabLayoutSelectedTextColor, 0) : mTabTextColors.getDefaultColor();
            mTabLayout.setTabTextColors(noSelected, selected);
        }
    }

    /**
     * 添加选项卡
     *
     * @param fragments
     * @param tabs      选项卡名称
     */
    public void addTab(List<Fragment> fragments, List<String> tabs) {
        for (int i = 0; i < tabs.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabs.get(i)));
        }
        mTabLayoutPagerAdapter = new TabLayoutPagerAdapter(mActivity.getSupportFragmentManager(), fragments, tabs);
        mViewPager.setAdapter(mTabLayoutPagerAdapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mTabLayoutPagerAdapter);
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
    public ViewPager getViewPager(){
        return mViewPager;
    }

}
