package com.yanxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yanxing.ui.R;

/**
 * 自定义进度条
 * Created by lishuangxiang on 2016/10/17.
 */
public class ProgressBar extends View {

    private Paint mPaint;

    //圆弧颜色
    private int mRoundColor;
    private float mRoundWidth;

    private int mTextColor;
    private float mTextSize;
    //进度百分比可见,默认true
    private boolean isTextVisible;

    private int mProgress;
    private int mProgressColor;
    private int mMax;
    //进度的风格，默认空心
    private int mStyle;
    //空心
    public static final int STROKE = 0;
    //实心
    public static final int FILL = 1;


    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mRoundColor = typedArray.getColor(R.styleable.ProgressBar_roundColor, Color.GRAY);
        mProgressColor = typedArray.getColor(R.styleable.ProgressBar_progressColor, Color.RED);
        mRoundWidth = typedArray.getDimension(R.styleable.ProgressBar_roundWidth, 5);
        mTextColor = typedArray.getColor(R.styleable.ProgressBar_textColor, Color.RED);
        mTextSize = typedArray.getDimension(R.styleable.ProgressBar_textSize, 32);
        isTextVisible = typedArray.getBoolean(R.styleable.ProgressBar_textVisible, true);
        mStyle = typedArray.getInt(R.styleable.ProgressBar_style, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆环
        int center = getWidth() / 2;
        int r = (int) (center - mRoundWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRoundColor);
        canvas.drawCircle(center, center, r, mPaint);

        //进度百分比
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        int percent = (int) ((mProgress * 1.0 / mMax) * 100);
        float textWidth = mPaint.measureText(percent + "%");
        if (isTextVisible && percent != 0) {
            canvas.drawText(percent + "%", center - textWidth / 2, center + mTextSize / 2, mPaint);
        }

        //圆弧
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setColor(mProgressColor);
        RectF oval = new RectF();
        switch (mStyle) {
            case STROKE: {
                mPaint.setStyle(Paint.Style.STROKE);
                oval.set(center - r, center - r, center + r, center + r);
                canvas.drawArc(oval, 0, 360 * mProgress / mMax, false, mPaint);
                break;
            }
            case FILL: {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                oval.set(center - r+16, center - r+16, center + r-16, center + r-16);
                canvas.drawArc(oval, 0, 360 * mProgress / mMax, true, mPaint);
                break;
            }
        }
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public boolean isTextVisible() {
        return isTextVisible;
    }

    public void setTextVisible(boolean textVisible) {
        isTextVisible = textVisible;
    }

    public int getMax() {
        return mMax;
    }

    public synchronized void setMax(int max) {
        mMax = max;
    }

    public int getProgress() {
        return mProgress;
    }

    public synchronized void setProgress(int progress) {
        if (progress > mMax) {
            mProgress = mMax;
        } else {
            mProgress = progress;
            postInvalidate();
        }
    }

    public int getRoundColor() {
        return mRoundColor;
    }

    public void setRoundColor(int roundColor) {
        mRoundColor = roundColor;
    }

    public float getRoundWidth() {
        return mRoundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        mRoundWidth = roundWidth;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int style) {
        mStyle = style;
    }
}
