package com.yanxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yanxing.ui.R;

/**
 * 来自http://blog.csdn.net/qq_22393017/article/details/59488906
 * 学习
 * Created by lishuangxiang on 2017/3/17.
 */
public class WheelView extends View {

    /**
     * 未选中项画笔
     */
    private Paint mPaintOuterText;
    /**
     * 选中项
     */
    private Paint mPaintCenterText;
    /**
     * 分割线
     */
    private Paint mPaintIndicator;

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPaints(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        mPaintOuterText=new Paint();
        mPaintOuterText.setColor(a.getColor(R.styleable.WheelView_outTextColor, Color.parseColor("#1b000000")));
        mPaintOuterText.setAntiAlias(true);
        mPaintOuterText.setTypeface(Typeface.MONOSPACE);
        mPaintOuterText.setTextSize(R.styleable.WheelView_outTextSize);

        mPaintCenterText=new Paint();
        mPaintCenterText.setColor(a.getColor(R.styleable.WheelView_centerTextColor,Color.parseColor("#2d2c2c")));
        mPaintCenterText.setAntiAlias(true);
        mPaintCenterText.setTextScaleX(1.1F);
        mPaintOuterText.setTypeface(Typeface.MONOSPACE);
        mPaintOuterText.setTextSize(R.styleable.WheelView_outTextSize);

        mPaintIndicator=new Paint();
        mPaintIndicator.setColor(a.getColor(R.styleable.WheelView_IndicatorTextColor,Color.parseColor("#1b000000")));
        mPaintIndicator.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void measureTextWidthHeigh(){
        Rect rect=new Rect();
//        for (int i=0;ad)
    }
}
