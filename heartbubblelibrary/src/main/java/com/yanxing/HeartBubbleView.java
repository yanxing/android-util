package com.yanxing;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * 类似直播送心效果
 * https://github.com/AlanCheen/PeriscopeLayout
 * Created by lishuangxiang on 2016/9/5.
 */
public class HeartBubbleView extends RelativeLayout {

    private Interpolator mLinearInterpolator = new LinearInterpolator();
    private Interpolator mAccelerateInterpolator = new AccelerateInterpolator();//加速
    private Interpolator mDecelerateInterpolator = new DecelerateInterpolator();//减速
    private Interpolator mAccelerateDecelerateInterpolator =
            new AccelerateDecelerateInterpolator();//先加速后减速
    private Interpolator mInterpolator[];

    private int mHigh;
    private int mWidth;
    private int mHeartHigh;//心高度
    private int mHeartWidth;
    private LayoutParams mLayoutParams;
    private Drawable mDrawable[];
    private Random mRandom = new Random();

    public HeartBubbleView(Context context) {
        this(context, null);
    }

    public HeartBubbleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HeartBubbleView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    /**
     * 初始化心图片
     * @param drawable 心图片
     */
    public void init(Drawable drawable[]){
        //默认的
        if (drawable==null||drawable.length==0){
            mDrawable = new Drawable[drawable.length];
            mDrawable[0] = getResources().getDrawable(R.mipmap.blue);
            mDrawable[1] = getResources().getDrawable(R.mipmap.pink);
            mDrawable[2] = getResources().getDrawable(R.mipmap.yellow);
        }else {
            mDrawable=drawable;
        }
        //假设三张图片大小一样
        mHeartHigh = mDrawable[0].getIntrinsicHeight();
        mHeartWidth = mDrawable[0].getMinimumWidth();

        //水平居中、底部
        mLayoutParams = new LayoutParams(mHeartHigh, mHeartWidth);
        mLayoutParams.addRule(CENTER_HORIZONTAL, TRUE);
        mLayoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        mInterpolator = new Interpolator[4];
        mInterpolator[0] = mLinearInterpolator;
        mInterpolator[1] = mAccelerateInterpolator;
        mInterpolator[2] = mDecelerateInterpolator;
        mInterpolator[3] = mAccelerateDecelerateInterpolator;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHigh = getMeasuredHeight();
    }

    /**
     * 开始一个心动画
     */
    public void start() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(mDrawable[mRandom.nextInt(mDrawable.length)]);
        imageView.setLayoutParams(mLayoutParams);
        addView(imageView);
        Animator animation = getAnimator(imageView);
        animation.addListener(new AnimEndListener(imageView));
        animation.start();
    }

    /**
     * 一个心的动画及运动轨迹
     *
     * @param target
     * @return
     */
    public Animator getAnimator(View target) {
        AnimatorSet animatorSet = getEnterAnimator(target);
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animatorSet, bezierValueAnimator);
        set.setInterpolator(mInterpolator[mRandom.nextInt(mInterpolator.length)]);
        set.setTarget(target);
        return set;
    }

    /**
     * 透明、缩放动画
     *
     * @param target
     * @return
     */
    public AnimatorSet getEnterAnimator(View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(alpha, scaleX, scaleY);
        animatorSet.setTarget(target);
        return animatorSet;
    }

    /**
     * 心的上升路径（贝塞尔曲线）
     *
     * @param target
     * @return
     */
    public ValueAnimator getBezierValueAnimator(View target) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(getPointF(2), getPointF(1));
        ValueAnimator animator = ValueAnimator.ofObject(bezierEvaluator,
                new PointF((mWidth - mHeartWidth) / 2, mHigh - mHeartHigh), new PointF(mRandom.nextInt(getWidth()), 0));
        animator.addUpdateListener(new BezierListener(target));
        animator.setDuration(2000);
        animator.setTarget(target);
        return animator;
    }

    /**
     * 贝塞尔曲线中间两点
     *
     * @param scale
     * @return
     */
    public PointF getPointF(int scale) {
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(mWidth - 100);//减去100 是为了控制 x轴活动范围
        pointF.y = mRandom.nextInt(mHigh-100) / scale;
        return pointF;
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener {

        private View mTarget;

        public BezierListener(View target) {
            this.mTarget = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF = (PointF) animation.getAnimatedValue();
            mTarget.setX(pointF.x);
            mTarget.setY(pointF.y);
            mTarget.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    private class AnimEndListener extends AnimatorListenerAdapter {

        private View mTarget;

        public AnimEndListener(View target) {
            this.mTarget = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView(mTarget);
        }
    }
}
