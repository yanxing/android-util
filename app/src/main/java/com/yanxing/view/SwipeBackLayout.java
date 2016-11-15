package com.yanxing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by lishuangxiang on 2016/11/15.
 */

public class SwipeBackLayout extends RelativeLayout implements View.OnTouchListener {

    /**
     * SwipeBackLayout父布局
     */
    private ViewGroup mSwipBackParent;
    /**
     * 处理滑动逻辑的View
     */
    private View mTouchView;
    /**
     * 能够识别的最小滑动距离
     */
    private int mTouchSlop;
    /**
     * 按下点的X坐标
     */
    private int mDownX;
    /**
     * 按下点的Y坐标
     */
    private int mDownY;
    /**
     * 临时X坐标
     */
    private int mTempX;
    /**
     * 滑动类
     */
    private Scroller mScroller;
    /**
     * SwipeBackLayout宽度
     */
    private int mSwipBackLayoutWidth;
    /**
     * 是否正在滑动
     */
    private boolean mIsSliding;
    /**
     * 是否滑动结束
     */
    private boolean mIsFinish;
    /**
     * 滑动监听
     */
    private OnSlidingFinishListener mOnSlidingFinishListener;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller=new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            mSwipBackParent= (ViewGroup) this.getParent();
            mSwipBackLayoutWidth=getWidth();
        }
    }

    /**
     * 设置滑动监听
     * @param onSlidingFinishListener
     */
    public void addOnSlidingFinishListener(OnSlidingFinishListener onSlidingFinishListener){
        this.mOnSlidingFinishListener=onSlidingFinishListener;
    }

    /**
     * 设置View触摸事件
     * @param targetView
     */
    public void setTouchView(View targetView){
        this.mTouchView=targetView;
        mTouchView.setOnTouchListener(this);
    }

    public View getTouchView() {
        return mTouchView;
    }

    /**
     * 向右滑出界面
     */
    private void scrollRight(){
        int x=mSwipBackLayoutWidth+mSwipBackParent.getScrollX();
        //向右为负
        mScroller.startScroll(mSwipBackParent.getScrollX(),0,-x+1,0,Math.abs(x));
        postInvalidate();
    }

    /**
     * 滚动其实位置
     */
    public void scrollOrigin(){
        int x=mSwipBackParent.getScrollX();
        mScroller.startScroll(mSwipBackParent.getScrollX(),0,-x,0,Math.abs(x));
        postInvalidate();
    }

    /**
     * 是否是AbsListView或者子类
     * @return
     */
    private boolean isTouchOnAbsListView(){
        return mTouchView instanceof AbsListView;
    }

    /**
     * 是否是ScrollView或者其子类
     * @return
     */
    private boolean isTouchOnScrollView(){
        return mTouchView instanceof ScrollView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){

        }
        return false;
    }

    /**
     * Activity滑动监听
     */
    public interface OnSlidingFinishListener {
        public void onSlidingFinish();
    }
}
