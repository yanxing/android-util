package com.yanxing.tablayoutlibrary;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 可以设置是否禁止Viewpager滑动
 * Created by 李双祥 on 2017/5/5.
 */
public class NoScrollViewPager extends ViewPager {

    private boolean mScroll =true;//false禁止viewpage滑动
    private boolean mOverideMeasure=false;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOverideMeasure) {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isOverideMeasure() {
        return mOverideMeasure;
    }

    /**
     * NestedScrollView嵌套viewpager嵌套fragment嵌套recyclerview，测量viewpager高度
     * @param overideMeasure
     */
    public void setOverideMeasure(boolean overideMeasure) {
        mOverideMeasure = overideMeasure;
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
