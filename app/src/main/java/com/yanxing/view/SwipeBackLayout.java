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
 * 作为学习用，按照http://blog.csdn.net/xiaanming/article/details/20934541
 * 重新写了一遍
 * Created by lishuangxiang on 2016/11/15.
 */
public class SwipeBackLayout extends RelativeLayout implements View.OnTouchListener {

    /**
     * SwipeBackLayout父布局
     */
    private ViewGroup mSwipeBackParent;
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
    private int mSwipeBackLayoutWidth;
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
            mSwipeBackParent = (ViewGroup) this.getParent();
            mSwipeBackLayoutWidth =this.getWidth();
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
        int x= mSwipeBackLayoutWidth + mSwipeBackParent.getScrollX();
        //向右为负
        mScroller.startScroll(mSwipeBackParent.getScrollX(),0,-x+1,0,Math.abs(x));
        postInvalidate();
    }

    /**
     * 滚动起始位置
     */
    public void scrollOrigin(){
        int x= mSwipeBackParent.getScrollX();
        mScroller.startScroll(mSwipeBackParent.getScrollX(),0,-x,0,Math.abs(x));
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
            case MotionEvent.ACTION_DOWN:
                mDownX= mTempX = (int) event.getRawX();
                mDownY= (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX= (int) event.getRawX();
                int deltaX=mTempX-moveX;
                mTempX=moveX;
                if (Math.abs(moveX-mDownX)>mTouchSlop&&
                        Math.abs((int) event.getRawY()-mDownY)<mTouchSlop){
                    mIsSliding=true;
                    //如果是AbListView,禁用item点击事件
                    if (isTouchOnAbsListView()){
                        MotionEvent motionEvent=MotionEvent.obtain(event);
                        motionEvent.setAction(MotionEvent.ACTION_CANCEL
                                |(event.getActionIndex()<<MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                        v.onTouchEvent(motionEvent);
                    }
                }

                if (moveX-mDownX>=0&&mIsSliding){
                    mSwipeBackParent.scrollBy(deltaX,0);
                    //屏蔽AbsListView和ScrollView自己的滑动事件
                    if (isTouchOnAbsListView()||isTouchOnScrollView()){
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsSliding=false;
                if (mSwipeBackParent.getScrollX()<=-mSwipeBackLayoutWidth /2){
                    mIsFinish=true;
                    scrollRight();
                }else {
                    scrollOrigin();
                    mIsFinish=false;
                }
                break;
        }
        //ScrollView和AbsListView处理自己的事件
        if (isTouchOnScrollView()||isTouchOnAbsListView()){
            return v.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public void computeScroll(){
        if (mScroller.computeScrollOffset()){
            mSwipeBackParent.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished()){
                if (mOnSlidingFinishListener!=null&&mIsFinish){
                    mOnSlidingFinishListener.onSlidingFinish();
                }
            }
        }
    }

    /**
     * Activity滑动监听
     */
    public interface OnSlidingFinishListener {
        void onSlidingFinish();
    }
}
