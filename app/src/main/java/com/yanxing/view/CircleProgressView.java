package com.yanxing.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
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
     * 画圆和刻度线
     */
    private Paint mPaint;

    /**
     * 画光标
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
     * 进度
     */
    private int mProgress = 25;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取视图宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 取最小值
        if (mWidth != mHeight) {
            int mMin = Math.min(mWidth, mHeight);
            mWidth = mMin;
            mHeight = mMin;
        }
        setMeasuredDimension(mWidth, mHeight);
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
    }

    /**
     * 初始设置
     */
    private void drawDefaultConfig(Canvas canvas) {
        // 圆
        setConfig(canvas, mPaint);
        // 进度圆
        setConfig(canvas, mPaint);
    }

    /**
     * 设置画笔相关默认属性
     *
     * @param canvas 画布
     * @param paint  画笔
     */
    private void setConfig(Canvas canvas, Paint paint) {
        paint.setAntiAlias(true);// 是否抗锯齿
        canvas.drawColor(Color.TRANSPARENT); // 设置画布为透明
        paint.setStyle(Paint.Style.STROKE);
        // 位置
        int w=getWidth();
        mRectF.left = 20; // 左上角X
        mRectF.top = 20; // 左上角Y
        mRectF.right = w-20 ; // 右下角X
        mRectF.bottom = w-20; // 右下角Y
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
        mPaint.setStrokeWidth(mProgressLineWidth);
        mPaint.setColor(mCircleProgressLineColor);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);
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
        float mRadius = (getMeasuredWidth() - mCirclePadding * 3) / 2-15;
        //X轴中点坐标
        int mCenterX = getMeasuredWidth() / 2;
        // 设置刻度线颜色
        mPaint.setColor(mCircleLineColor);
        mPaint.setStrokeWidth(mScaleLineWidth);
        // 绘制100份线段，切分空心圆弧
        for (float i = 0; i < 360; i += 3.6) {
            double mArc = i * Math.PI / 180;
            float mStartX = (float) (mCenterX + (mRadius - mCircleBorderWidth) * Math.sin(mArc));
            float mStartY = (float) (mCenterX + (mRadius - mCircleBorderWidth) * Math.cos(mArc));
            float mStopX = (float) (mCenterX + mRadius * Math.sin(mArc) + 1);
            float mStopY = (float) (mCenterX + mRadius * Math.cos(mArc) + 1);
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
        float mRadius = getMeasuredWidth() / 2-20;
        // 计算从正上方旋转了多少度
        float mArc = ((float) mProgress / mMaxProgress) * 360;
        // 计算圆弧上X,Y轴坐标
        float mIndicatorX = mRectF.centerX() + (float)(mRadius * Math.cos(Math.toRadians(mArc + 270)));
        float mIndicatorY = mRectF.centerY()+ (float)(mRadius * Math.sin(Math.toRadians(mArc + 270)));
        // 设置相关属性
        mIndicatorPaint.setAntiAlias(false);
        mIndicatorPaint.setColor(Color.TRANSPARENT);
        mIndicatorPaint.setStyle(Paint.Style.FILL);
        mIndicatorPaint.setColor(mCircleProgressLineColor);
        // 设置过滤器
        mIndicatorPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL)); //需关闭硬件加速
        canvas.drawCircle(mIndicatorX, mIndicatorY, 12, mIndicatorPaint);
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
        ValueAnimator percentAnimator = ValueAnimator.ofInt(0, mProgress);
        percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        percentAnimator.setDuration(1000);
        percentAnimator.start();
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
