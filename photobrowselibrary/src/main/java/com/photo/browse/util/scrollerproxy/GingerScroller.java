package com.photo.browse.util.scrollerproxy;

import android.content.Context;
import android.widget.OverScroller;

/**
 * Created by Gavin on 2015/8/24.
 */
public class GingerScroller extends ScrollerProxy{

    protected final OverScroller mScroller;

    private boolean mFirstScroll = false;

    public GingerScroller(Context context) {
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean computeScrollOffset() {
        if (mFirstScroll) {
            mScroller.computeScrollOffset();
            mFirstScroll = false;
        }
        return mScroller.computeScrollOffset();
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    @Override
    public void forceFinished(boolean finished) {
        mScroller.forceFinished(finished);
    }

    @Override
    public boolean isFinished() {
        return mScroller.isFinished();
    }

    @Override
    public int getCurrX() {
        return mScroller.getCurrX();
    }

    @Override
    public int getCurrY() {
        return mScroller.getCurrY();
    }
}
