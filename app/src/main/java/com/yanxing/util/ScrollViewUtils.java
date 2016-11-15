package com.yanxing.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;

import com.refresh.lib.RefreshLayout;

/**
 * Created by pc on 2016/9/1.
 *
 * @author lvke
 */
public class ScrollViewUtils {

    private static boolean canStartAnimation = true;//是否开始动画
    private static boolean isShow = true;//HeadView是否显示
    private static final long durationMillis = 200;
    private static long scrollDistance;
    private static boolean isDownSlide = false;//是否正在下滑

    private static int mLastY;

    private static boolean mIsSliding;

    public static void initHideShowAnimation(Context context,RecyclerView scrollView, final View headView,
                                             final View footView, final RefreshLayout refreshLayout) {
        int touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
        LogUtil.d("ScrollViewUtil","touchSlop="+touchSlop);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        headView.measure(w, h);
        footView.measure(w, h);
        final float headViewHeight = (float) headView.getMeasuredHeight();
        final float footViewHeight = (float) footView.getMeasuredHeight();
        LogUtil.e("ScrollViewUtil","headViewHeight==" + headViewHeight + "  footViewHeight==" + footViewHeight);

        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

//            @Override
           /* public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.e("ScrollView","newState==" + newState);
                switch (newState) {

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态0
                        if (scrollDistance == 0 && !canStartAnimation && !isDownSlide) {
                            LogUtil.e("ScrollViewUtil","+++++++++scrollDistance == 0乡下滑动");
                            Animation headViewAnimation = new TranslateAnimation(0.1f, 0.1f, -headViewHeight, 0.1f);
                            //设置动画时间
                            headViewAnimation.setDuration(durationMillis);
                            headViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    LogUtil.e("ScrollView","+++++++++scrollDistance == 0Start正常isShow = " + isShow);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    headView.setVisibility(View.VISIBLE);
                                    isShow = true;
                                    LogUtil.e("ScrollView","+++++++++scrollDistance == 0Start正常isShow = " + isShow);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            headView.startAnimation(headViewAnimation);

                            Animation footViewAnimation = new TranslateAnimation(0.1f, 0.1f, footViewHeight, 0.1f);
                            //设置动画时间
                            footViewAnimation.setDuration(durationMillis);
                            footViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    footView.setVisibility(View.VISIBLE);
                                    canStartAnimation = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            footView.startAnimation(footViewAnimation);
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸滚动1

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://松开后滚动2

                        break;
                }
            }*/

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //上滑
                if (dy>0){
                    if (isShow){

                    }
                }
//                refreshLayout.setPadding(0, dy, 0, 0);
                LogUtil.e("ScrollView","dx==" + dx + "    dy==" + dy);
                scrollDistance = scrollDistance + dy;
                LogUtil.e("ScrollView","scrollDistance==" + scrollDistance + "canStartAnimation = " + canStartAnimation);

                /*是否开始动画*/
//                if (canStartAnimation) {

                 /*如果头部视图显示*/
                    if (isShow) {
                    /*向上滚动*/
                        if (dy > 0 && scrollDistance > headViewHeight) {
                            canStartAnimation = false;
                            Animation headViewAnimation = new TranslateAnimation(0.1f, 0.1f, 0.1f, -headViewHeight);
                            //设置动画时间
                            headViewAnimation.setDuration(durationMillis);
                            headViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    headView.setVisibility(View.GONE);
                                    isShow = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            headView.startAnimation(headViewAnimation);

                            Animation footViewAnimation = new TranslateAnimation(0.1f, 0.1f, 0.1f, headViewHeight);
                            //设置动画时间
                            footViewAnimation.setDuration(durationMillis);
                            footViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    footView.setVisibility(View.GONE);
                                    canStartAnimation = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            footView.startAnimation(footViewAnimation);
                        }
                    }

                    /*如果BottomView显示*/
                    if (!isShow) {
                        /*向下滚动*/
                        if (dy < 0 || scrollDistance == 0) {
                            LogUtil.e("ScrollView","+++++++++正常乡下滑动");
                            canStartAnimation = false;
                            isDownSlide = true;
                            Animation headViewAnimation = new TranslateAnimation(0.1f, 0.1f, -headViewHeight, 0.1f);
                            //设置动画时间
                            headViewAnimation.setDuration(durationMillis);
                            headViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    LogUtil.e("ScrollView","+++++++++Start正常isShow = " + isShow);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    headView.setVisibility(View.VISIBLE);
                                    isShow = true;
                                    LogUtil.e("ScrollView","+++++++++End正常isShow = " + isShow);
                                    isDownSlide = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            headView.startAnimation(headViewAnimation);

                            Animation footViewAnimation = new TranslateAnimation(0.1f, 0.1f, footViewHeight, 0.1f);
                            //设置动画时间
                            footViewAnimation.setDuration(durationMillis);
                            footViewAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    footView.setVisibility(View.VISIBLE);
                                    canStartAnimation = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            footView.startAnimation(footViewAnimation);

                        }
                    }
                }
//            }

        };
        scrollView.addOnScrollListener(onScrollListener);
    }

}
