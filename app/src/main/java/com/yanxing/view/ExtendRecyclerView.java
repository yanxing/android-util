package com.yanxing.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * Created by lishuangxiang on 2016/11/11.
 */

public class ExtendRecyclerView extends RecyclerView {

    private boolean mIsScroll=true;

    public ExtendRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendRecyclerView(Context context) {
        super(context);
    }

    /**
     * 禁用上滑和下滑
     */
    public void setScrollEnabled(boolean enabled){
        this.mIsScroll=enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e)&&mIsScroll;
    }

}
