package com.yanxing.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * RecycleView上滑隐藏标题栏和底部导航栏View，下滑显示
 * Created by lishuangxiang on 2016/11/16.
 */

public class RecycleViewUtil {

    private static boolean mIsShow = true;
    private static int mLastY = 0;

    private static boolean mIsSliding=false;

    private static final String TAG="RecycleViewUtil";

    /**
     * 隐藏或显示标题栏和底部导航栏View
     *
     * @param scrollView
     * @param headView   标题栏
     * @param footView   底部导航
     */
    public static void startHideShowAnimation(RecyclerView scrollView, final View headView,
                                              final View footView) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        headView.measure(w, h);
        footView.measure(w, h);
        final int headViewHeight =headView.getMeasuredHeight();
        final int footViewHeight =footView.getMeasuredHeight();
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.d(TAG,"dx="+dx+"  dy="+dy);
                /**
                 * 防止快速反向滑动，造成headView和footView闪屏，2和60根据实际效果设置
                 */
                if (Math.abs(dy) < 4 || Math.abs(dy - mLastY) > 60||mIsSliding) {
                    mLastY=dy;
                    return;
                }
                LogUtil.d(TAG,"mLastY"+mLastY);
                mLastY = dy;
                //上滑,向下翻页时
                if (dy > 0) {
                    if (mIsShow) {
                        mIsShow = false;
                        ValueAnimator animator = createDropAnimator(headView, headViewHeight, 0);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                headView.setVisibility(View.GONE);
                            }
                        });
                        animator.start();

                        ValueAnimator footAnimator = createDropAnimator(footView, footViewHeight, 0);
                        footAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                footView.setVisibility(View.GONE);
                                mIsSliding=false;
                            }
                        });
                        footAnimator.start();
                    }
                } else if (dy < 0) {
                    if (!mIsShow) {
                        mIsShow = true;
                        ValueAnimator animator = createDropAnimator(headView, 0,headViewHeight);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                headView.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.start();

                        ValueAnimator footAnimator = createDropAnimator(footView, 0,footViewHeight);
                        footAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                footView.setVisibility(View.VISIBLE);
                                mIsSliding=false;
                            }
                        });
                        footAnimator.start();
                    }
                }
            }
        };
        scrollView.addOnScrollListener(onScrollListener);
    }

    /**
     * 更新view大小
     * @return
     */
    private static ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int value = (Integer) valueAnimator.getAnimatedValue();// 得到的值
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = value;
                        view.setLayoutParams(layoutParams);
                        mIsSliding=true;
                    }
                }
        );
        return animator;
    }
}
