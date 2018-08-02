package com.yanxing.tablayoutlibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可以设置是否禁止Viewpager滑动
 * Created by 李双祥 on 2017/5/5.
 */
public class NoScrollViewPager extends ViewPager {

    private boolean mScroll =true;//false禁止viewpage滑动

    public NoScrollViewPager(Context context){
        super(context);
    }
    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return mScroll &&super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return mScroll &&super.onTouchEvent(arg0);
    }

    public boolean isScroll() {
        return mScroll;
    }

    public void setScroll(boolean scroll) {
        mScroll = scroll;
    }
}
