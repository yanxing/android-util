package com.yanxing.tablayoutlibrary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.ViewCompat;
import androidx.appcompat.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


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
            //反射改变tabview的背景色
            Class<?> tabClass = tab.getClass();
            Field field;
            try {
                field = tabClass.getDeclaredField("mView");
                field.setAccessible(true);
                ViewCompat.setBackground((View) field.get(tab), AppCompatResources.getDrawable(getContext(), mTabBackgroundResId));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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
     * 修改tabLayout下划线宽度
     *
     * @param tabLayout
     * @param padding
     */
    public static void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

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
    public NoScrollViewPager getViewPager() {
        return mViewPager;
    }

}
