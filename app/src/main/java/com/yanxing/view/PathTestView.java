package com.yanxing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 测试path类
 * Created by lishuangxiang on 2016/8/2.
 */
public class PathTestView extends View {

    private Paint mPaint;

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint= new Paint();
        mPaint.setColor(Color.RED);
        //设置画笔宽度
        mPaint.setStrokeWidth(40);
        //消除锯齿
        mPaint.setAntiAlias(true);
        //设置镂空（方便查看效果）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
//        path.moveTo(20, 50);
        path.lineTo(50, 200);
        path.quadTo(100, 200, 250, 350);
        canvas.drawLine(300, 400, 500, 600, mPaint);
        canvas.drawPath(path, mPaint);
    }
}
