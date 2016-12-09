package com.yanxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yanxing.ui.R;


/**
 * 作者：Gavin 时间：2016/12/9
 * 描述：CircleProgressView
 */
public class CircleProgressView extends View {

    private Context mContext;

    /**
     * 视图宽高
     */
    private int mWidth;
    private int mHeight;
    /**
     * 画圆所在的距形区域
     */
    private RectF mRectF;

    /**
     * 圆
     */
    private Paint mPaint;

    /**
     * 进度
     */
    private Paint mProgressPaint;

    /**
     * 光标
     */
    private Paint mIndicatorPaint;

    /**
     * 圆线条宽度
     */
    private int mStrokeWidth = 5;

    /**
     * 进度圆线条宽度
     */
    private int mProgressLineWidth = 8;

    /**
     * 刻度线宽度
     */
    private int mScaleLineWidth = 2;

    /**
     * 半径
     */
    private int mRadius;

    /**
     * 进度
     */
    private int mProgress = 20;

    /**
     * 进度最大值
     */
    private int mMaxProgress;

    /**
     * 线条颜色
     */
    private int mCircleLineColor;

    /**
     * 进度条颜色
     */
    private int mCircleProgressLineColor;

    /**
     * 圆弧线宽
     */
    private float mCircleBorderWidth;

    /**
     * 内边距
     */
    private float mCirclePadding;

    /**
     * 标题文本大小
     */
    private int mTitleTextSize;

    /**
     * 内容文本大小
     */
    private int mContentTextSize;

    /**
     * 标题字体颜色
     */
    private int mTitleTextColor;

    /**
     * 内容字体颜色
     */
    private int mContentTextColor;

    /**
     * 标题文本
     */
    private String mTitleText;

    /**
     * 内容文本
     */
    private String mContentText;


    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 自定义属性
        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mCircleLineColor = mArray.getColor(R.styleable.CircleProgressView_circleLineColor, Color.WHITE);
        mCircleProgressLineColor = mArray.getColor(R.styleable.CircleProgressView_circleProgressLineColor, Color.BLACK);
        mMaxProgress = mArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleMaxProgress, 100);
        mCircleBorderWidth = mArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleLineBorderWidth, dip2px(5));
        mCirclePadding = mArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleLinePadding, dip2px(5));
        mTitleTextSize = mArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleTextSize, sp2px(12));
        mContentTextSize = mArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleTextSize, sp2px(12));
        mTitleTextColor = mArray.getColor(R.styleable.CircleProgressView_circleTextColor, Color.BLACK);
        mContentTextColor = mArray.getColor(R.styleable.CircleProgressView_circleTextColor, Color.BLACK);
        mArray.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawDefaultConfig(canvas);// 初始设置
        drawCircle(canvas);// 绘制圆
        drawScaleLine(canvas);// 绘制刻度线
        drawIndicator(canvas);// 绘制光标
        drawTitleText(canvas);// 绘制标题文本
        drawContentText(canvas);// 绘制内容文本
        canvas.restore();
    }

    /**
     * 初始设置
     */
    private void drawDefaultConfig(Canvas canvas) {
        // 获取视图宽高
        mWidth = this.getWidth();
        mHeight = this.getHeight();
        // 取最小值
        if (mWidth != mHeight) {
            int mMin = Math.min(mWidth, mHeight);
            mWidth = mMin;
            mHeight = mMin;
        }
        // 半径
        mRadius = getMeasuredWidth() / 4;
        // 圆
        setConfig(canvas, mPaint, mStrokeWidth);
        // 进度圆
        setConfig(canvas, mProgressPaint, mProgressLineWidth);
    }

    /**
     * 设置画笔相关默认属性
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param width  线条宽度
     */
    private void setConfig(Canvas canvas, Paint paint, int width) {
        paint.setAntiAlias(true);// 是否抗锯齿
        canvas.drawColor(Color.TRANSPARENT); // 设置画布为透明
        paint.setStyle(Paint.Style.STROKE);
        // 位置
        mRectF.left = width / 2; // 左上角X
        mRectF.top = width / 2; // 左上角Y
        mRectF.right = mWidth - width / 2; // 右下角X
        mRectF.bottom = mHeight - width / 2; // 右下角Y
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        // 绘制底层圆
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mCircleLineColor);
        canvas.drawArc(mRectF, -90, 360, false, mPaint);

        // 绘制进度圆
        mProgressPaint.setStrokeWidth(mProgressLineWidth);
        mProgressPaint.setColor(mCircleProgressLineColor);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mProgressPaint);
        canvas.restore();
    }

    /**
     * 绘制刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        canvas.save();
        //半径
        float mRadius = (getMeasuredWidth() - mCirclePadding * 3) / 2;
        //X轴中点坐标
        int mCenterX = getMeasuredWidth() / 2;
        // 设置刻度线颜色
        mPaint.setColor(mCircleLineColor);
        mPaint.setStrokeWidth(mScaleLineWidth);
        // 绘制100份线段，切分空心圆弧
        for (float i = 0; i < 360; i += 3.6) {
            double mRad = i * Math.PI / 180;
            float mStartX = (float) (mCenterX + (mRadius - mCircleBorderWidth) * Math.sin(mRad));
            float mStartY = (float) (mCenterX + (mRadius - mCircleBorderWidth) * Math.cos(mRad));
            float mStopX = (float) (mCenterX + mRadius * Math.sin(mRad) + 1);
            float mStopY = (float) (mCenterX + mRadius * Math.cos(mRad) + 1);
            canvas.drawLine(mStartX, mStartY, mStopX, mStopY, mPaint);
        }
        canvas.restore();
    }

    /**
     * 绘制光标
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        float mSweep = ((float) mProgress / mMaxProgress) * 360;
        mIndicatorPaint.setStyle(Paint.Style.FILL);
        mIndicatorPaint.setColor(Color.RED);
        float f=((float) mProgress / mMaxProgress) * 360;

        mIndicatorPaint.setMaskFilter(new BlurMaskFilter(dip2px(3), BlurMaskFilter.Blur.NORMAL)); //需关闭硬件加速
        float x = (float) ((mRadius + dip2px(10)) * Math.cos(Math.toRadians(f + mSweep)));
        float y = (float) ((mRadius + dip2px(10)) * Math.sin(Math.toRadians(f + mSweep)));
        canvas.drawCircle(200, y, dip2px(10), mIndicatorPaint);
        canvas.restore();
    }

    /**
     * 绘制标题文本
     *
     * @param canvas
     */
    private void drawTitleText(Canvas canvas) {
        if (!TextUtils.isEmpty(mTitleText)) {
            canvas.save();
            mPaint.setColor(mTitleTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(sp2px(mTitleTextSize));
            int mTextHeight = mHeight / 4;
            int mTextWidth = (int) mPaint.measureText(mTitleText, 0, mTitleText.length());
            canvas.drawText(mTitleText, (mWidth / 2) - (mTextWidth / 2), (mHeight / 4) + (mTextHeight / 2), mPaint);
            canvas.restore();
        }
    }

    /**
     * 绘制标题文本
     *
     * @param canvas
     */
    private void drawContentText(Canvas canvas) {
        if (!TextUtils.isEmpty(mContentText)) {
            canvas.save();
            mPaint.setColor(mContentTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(sp2px(mContentTextSize));
            int mTextHeight = mHeight / 4;
            int mTextWidth = (int) mPaint.measureText(mContentText, 0, mContentText.length());
            canvas.drawText(mContentText, (mWidth / 2) - (mTextWidth / 2), ((mHeight / 4) + (mTextHeight / 2)) * 1.8F, mPaint);
            canvas.restore();
        }
    }

    private int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, mContext.getResources().getDisplayMetrics());
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public void setCircleLineColor(int circleLineColor) {
        this.mCircleLineColor = circleLineColor;
    }

    public void setCircleProgressLineColor(int circleProgressLineColor) {
        this.mCircleProgressLineColor = circleProgressLineColor;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    public void setScaleLineWidth(int scaleLineWidth) {
        this.mScaleLineWidth = scaleLineWidth;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.mTitleTextSize = titleTextSize;
    }

    public void setContentTextSize(int contentTextSize) {
        this.mContentTextSize = contentTextSize;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.mTitleTextColor = titleTextColor;
    }

    public void setContentTextColor(int contentTextColor) {
        this.mContentTextColor = contentTextColor;
    }

    public void setTitleText(String titleText) {
        this.mTitleText = titleText;
    }

    public void setContentText(String contentText) {
        this.mContentText = contentText;
    }

    public void setCircleBorderWidth(float circleBorderWidth) {
        this.mCircleBorderWidth = circleBorderWidth;
    }

    public void setCirclePadding(float circlePadding) {
        this.mCirclePadding = circlePadding;
    }
}
