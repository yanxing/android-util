package com.yanxing.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.yanxing.ui.R;

/**
 * 芝麻分学习 http://blog.csdn.net/ccy0122/article/details/53241648
 * 有修改
 * Created by lishuangxiang on 2016/12/6.
 */
public class SesameView extends View {

    /**
     * 最大数值
     */
    private int mMaxNum;
    /**
     * 开始刻度
     */
    private int mStartAngle;
    /**
     * 扫过的角度
     */
    private int mSweepAngle;

    private int mWidth;
    private int mHigh;
    /**
     * 内环宽度
     */
    private int mSweepInWidth;
    /**
     * 外环宽度
     */
    private int mSweepOutWidth;
    private int mRadius;
    private int mCurrentNum;

    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private static final String[] TEXT = {"较差", "中等", "良好", "优秀", "极好"};
    private static final int[] INDICATOR_COLOR = {0xffffffff, 0x00ffffff, 0x99ffffff, 0xffffffff};

    public SesameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SesameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SesameView);
        mMaxNum = typedArray.getInt(R.styleable.SesameView_maxNum, 900);
        mStartAngle = typedArray.getInt(R.styleable.SesameView_startAngle, 160);
        mSweepAngle = typedArray.getInt(R.styleable.SesameView_sweepAngle, 220);
        mSweepInWidth = dp2px(8);
        mSweepOutWidth = dp2px(3);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffffffff);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 转换dp为px
     */
    public int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f * dp);
    }

    /**
     * 转换sp为px
     */
    public int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode == MeasureSpec.EXACTLY) {
            mWidth = wSize;
        } else {
            mWidth = dp2px(300);
        }

        if (hMode == MeasureSpec.EXACTLY) {
            mHigh = hSize;
        } else {
            mHigh = dp2px(400);
        }
        setMeasuredDimension(mWidth, mHigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRadius = getMeasuredWidth() / 4;
        canvas.save();
        canvas.translate(mWidth / 2, mWidth / 2);
        drawRound(canvas);  //画内外圆弧
        drawScale(canvas);//画刻度
        drawIndicator(canvas); //画当前进度值
        drawCenterText(canvas);//画中间的文字
        canvas.restore();
    }

    /**
     * 画内外圆弧
     *
     * @param canvas
     */
    private void drawRound(Canvas canvas) {
        canvas.save();
        //内圆
        mPaint.setAlpha(0x40);
        mPaint.setStrokeWidth(mSweepInWidth);
        RectF rectf = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(rectf, mStartAngle, mSweepAngle, false, mPaint);
        //外圆
        mPaint.setStrokeWidth(mSweepOutWidth);
        int w = dp2px(10);
        RectF rectf2 = new RectF(-mRadius - w, -mRadius - w, mRadius + w, mRadius + w);
        canvas.drawArc(rectf2, mStartAngle, mSweepAngle, false, mPaint);
        canvas.restore();
    }

    /**
     * 画刻度
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        float angle = (float) mSweepAngle / 30;//刻度间隔
        canvas.rotate(-270 + mStartAngle);//将起始刻度点旋转到正上方(270度)
        for (int i = 0; i <= 30; i++) {
            if (i % 6 == 0) {//画粗刻度
                mPaint.setStrokeWidth(dp2px(2));
                mPaint.setAlpha(0x70);
                canvas.drawLine(0, -mRadius - mSweepInWidth / 2, 0, -mRadius + mSweepInWidth / 2+dp2px(2), mPaint);
                drawText(canvas, i * mMaxNum / 30 + "", mPaint);
            } else {//画细线
                mPaint.setStrokeWidth(dp2px(1));
                mPaint.setAlpha(0x50);
                canvas.drawLine(0, -mRadius - mSweepInWidth / 2, 0, -mRadius + mSweepInWidth / 2, mPaint);
            }
            //画刻度间文字
            if (i == 3 || i == 9 || i == 15 || i == 21 || i == 27) {
                mPaint.setStrokeWidth(dp2px(2));
                mPaint.setAlpha(0x50);
                drawText(canvas, TEXT[(i - 3) / 6], mPaint);
            }
            canvas.rotate(angle);//逆时针
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas, String text, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(sp2px(12));
        float width=paint.measureText(text);
        canvas.drawText(text, -width / 2, -mRadius + dp2px(16), paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 画进度
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        mPaint1.setStyle(Paint.Style.STROKE);
        int sweep;
        if (mCurrentNum <= mMaxNum) {
            sweep = (int) ((float) mCurrentNum / (float) mMaxNum * mSweepAngle);
        } else {
            sweep = mSweepAngle;
        }
        mPaint1.setStrokeWidth(mSweepOutWidth);
        Shader shader = new SweepGradient(0, 0, INDICATOR_COLOR, null);
        mPaint1.setShader(shader);
        int w = dp2px(10);
        RectF rectf = new RectF(-mRadius - w, -mRadius - w, mRadius + w, mRadius + w);
        canvas.drawArc(rectf, mStartAngle, sweep, false, mPaint1);
        float x = (float) ((mRadius + dp2px(10)) * Math.cos(Math.toRadians(mStartAngle + sweep)));
        float y = (float) ((mRadius + dp2px(10)) * Math.sin(Math.toRadians(mStartAngle + sweep)));
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(0xffffffff);
        //内外模糊处理，发光效果
        mPaint2.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.NORMAL)); //需关闭硬件加速
        canvas.drawCircle(x, y, dp2px(5), mPaint2);
        canvas.restore();
    }

    /**
     * 画中间文字
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        canvas.save();
        mPaint3.setStyle(Paint.Style.FILL);
        mPaint3.setTextSize(mRadius / 2);
        mPaint3.setColor(0xffffffff);
        canvas.drawText(mCurrentNum + "", -mPaint3.measureText(mCurrentNum + "") / 2, 0, mPaint3);
        mPaint3.setTextSize(mRadius / 4);
        String content = "信用";
        if (mCurrentNum < mMaxNum / 5) {
            content += TEXT[0];
        } else if (mCurrentNum >= mMaxNum/ 5 && mCurrentNum < mMaxNum * 2 / 5) {
            content += TEXT[1];
        } else if (mCurrentNum >= mMaxNum * 2 / 5 && mCurrentNum < mMaxNum * 3 / 5) {
            content += TEXT[2];
        } else if (mCurrentNum >= mMaxNum * 3 / 5 && mCurrentNum < mMaxNum * 4 / 5) {
            content += TEXT[3];
        } else if (mCurrentNum >= mMaxNum * 4 / 5) {
            content += TEXT[4];
        }
        Rect r = new Rect();
        mPaint3.getTextBounds(content, 0, content.length(), r);
        canvas.drawText(content, -r.width() / 2, r.height() + 20, mPaint3);
        canvas.restore();
    }

    public int getCurrentNum() {
        return mCurrentNum;
    }

    public void setCurrentNum(int currentNum) {
        mCurrentNum = currentNum;
        int color = calculateColor(currentNum);
        setBackgroundColor(color);
        invalidate();
    }

    private int calculateColor(int value) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        float fraction;
        int color;
        if (value <= mMaxNum / 2) {
            fraction = value / (mMaxNum / 2);
            color = (int) evaluator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00);
        } else {
            fraction = ((float) value - mMaxNum / 2) / (mMaxNum / 2);
            color = (int) evaluator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1); //由橙到蓝
        }
        return color;
    }

}
