package com.yanxing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lishuangxiang on 2016/11/1.
 */

public class TextViewImage extends View {

    private Paint mPaint;

    private Bitmap mFrontBitmap;
    private Bitmap mBackBitmap;

    private String mContent;

    public TextViewImage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mFrontBitmap,8,8,mPaint);

        //文字
        mPaint.setTextSize(36);
        mPaint.setColor(Color.RED);
        int width=mFrontBitmap.getWidth();
        int heigh=mFrontBitmap.getHeight();
        canvas.drawText(mContent,90,46,mPaint);

        //后面的图片
//        canvas.drawBitmap(mBackBitmap,80,40,mPaint);

    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Bitmap getBackBitmap() {
        return mBackBitmap;
    }

    public void setBackBitmap(Bitmap backBitmap) {
        mBackBitmap = backBitmap;
    }

    public Bitmap getFrontBitmap() {
        return mFrontBitmap;
    }

    public void setFrontBitmap(Bitmap frontBitmap) {
        mFrontBitmap = frontBitmap;
    }
}
