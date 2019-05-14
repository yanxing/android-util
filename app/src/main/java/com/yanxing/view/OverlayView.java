package com.yanxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.yanxing.ui.R;

/**
 * @author 李双祥 on 2019/5/13.
 */
public class OverlayView extends View {

    private Paint mPaint;
    private int mOverlayColor;
    private int mOverlayImgTop;
    private int mOverlayImgSize;
    private int mOverlayRadius;
    private Bitmap mBitmap;
    private PorterDuffXfermode mPorterDuffXfermode;

    public OverlayView(Context context) {
        this(context, null);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OverlayView);
        mOverlayColor = a.getColor(R.styleable.OverlayView_overlayColor, 0xffffffff);
        mOverlayImgTop = (int) a.getDimension(R.styleable.OverlayView_overlayImgTop, dp2px(getContext(), 8));
        mOverlayImgSize = (int) a.getDimension(R.styleable.OverlayView_overlayImgSize, dp2px(getContext(), 28));
        mOverlayRadius = (int) a.getDimension(R.styleable.OverlayView_overlayRadius, dp2px(getContext(), 36));
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        int width = mOverlayRadius * 2;
        int height = mOverlayRadius * 2;
        canvas.drawBitmap(createDstBigmap(width, height), 0, 0, mPaint);
        mPaint.setXfermode(mPorterDuffXfermode);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, (width - mBitmap.getWidth()) / 2, mOverlayImgTop, mPaint);
        }
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }

    /**
     * 圆
     *
     * @param width
     * @param height
     * @return
     */
    private Bitmap createDstBigmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint scrPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scrPaint.setColor(mOverlayColor == 0 ? 0xffffffff : mOverlayColor);
        canvas.drawCircle(width / 2, height / 2, width / 2, scrPaint);
        return bitmap;
    }

    /**
     * 设置图片
     *
     * @param bitmap
     */
    public void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = createBitmapZoop(bitmap, mOverlayImgSize, mOverlayImgSize);
            invalidate();
        }
    }

    /**
     * 设置圆颜色
     *
     * @param overlayColor
     */
    public void setOverlayColor(int overlayColor) {
        mOverlayColor = overlayColor;
        invalidate();
    }

    /**
     * 设置圆和图片
     *
     * @param bitmap
     * @param overlayColor
     */
    public void setOverlay(Bitmap bitmap, int overlayColor) {
        if (bitmap != null) {
            mBitmap = createBitmapZoop(bitmap, mOverlayImgSize, mOverlayImgSize);
        }
        mOverlayColor = overlayColor;
        invalidate();
    }

    /**
     * 缩放成设定大小
     *
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    private Bitmap createBitmapZoop(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 返回新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
    }

    /**
     * 转换dp为px
     */
    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
