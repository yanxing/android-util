package com.yanxing.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yanxing.model.Point;
import com.yanxing.ui.animation.PointTypeEvaluator;

/**
 * Created by lishuangxiang on 2016/8/2.
 */
public class MyAnimView extends View {

    private Point mPoint;
    private Paint mPaint;
    public static final float RADIUS = 50;


    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPoint == null) {
            mPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    public void drawCircle(Canvas canvas) {
        float x = mPoint.getX();
        float y = mPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointTypeEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(5000);
        anim.start();
    }
}
