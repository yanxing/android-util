package com.yanxing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.yanxing.ui.R;

import java.util.ArrayList;

/**
 * 水波纹学习 http://blog.csdn.net/singwhatiwanna/article/details/42614953
 * Created by lishuangxiang on 2016/12/5.
 */

public class RippleLayout extends LinearLayout implements Runnable {

    private View mTouchTarget;
    private boolean mIsPressed;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTargetWidth;
    private int mTargetHeight;

    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;

    private int mMaxRevealRadius;
    private int mRevealRadiusGap;
    private int mRevealRadius = 0;

    private float mCenterX;
    private float mCenterY;
    private int[] mLocationInScreen = new int[2];

    private boolean mShouldDoAnimation = false;
    private int INVALIDATE_DURATION = 40;
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();

    public RippleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(getResources().getColor(R.color.reveal_color));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            View touchView = getTouchTarget(this, x, y);
            if (touchView.isClickable() && touchView.isEnabled()) {
                mTouchTarget = touchView;
                initParametersForChild(ev, touchView);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = ev;
            postDelayed(mDispatchUpTouchEventRunnable, 400);
            return true;

        } else if (action == MotionEvent.ACTION_CANCEL) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initParametersForChild(MotionEvent event, View view) {
        mCenterX = event.getX();
        mCenterY = event.getY();
        mTargetWidth = view.getMeasuredWidth();
        mTargetHeight = view.getMeasuredHeight();
        mMinBetweenWidthAndHeight = Math.min(mTargetWidth, mTargetHeight);
        mMaxBetweenWidthAndHeight = Math.max(mTargetWidth, mTargetHeight);
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mRevealRadiusGap = mMinBetweenWidthAndHeight / 8;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int transformedCenterX = (int) mCenterX - left;
        mMaxRevealRadius = Math.max(transformedCenterX, mTargetWidth - transformedCenterX);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null) {
            return;
        }
        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
            mRevealRadius += mRevealRadiusGap * 4;
        } else {
            mRevealRadius += mRevealRadiusGap;
        }

        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        if (mRevealRadius <= mMaxRevealRadius) {
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }
    }

    @Override
    public boolean performClick() {
        postDelayed(this, 400);
        return true;
    }

    /**
     * 点击的View
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> views = view.getTouchables();
        for (View v : views) {
            if (isTouchPointInView(v, x, y)) {
                target = v;
                break;
            }
        }
        return target;
    }

    public boolean isTouchPointInView(View view, int x, int y) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        super.performClick();
    }

    private class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
            if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int) event.getRawX(), (int) event.getRawY())) {
                mTouchTarget.dispatchTouchEvent(event);
            }
        }
    }

}
