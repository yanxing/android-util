package com.yanxing.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanxing.base.BaseFragment;
import com.yanxing.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;

/**
 * 文字转化为图片，宽度一致，绘画
 * Created by lishuangxiang on 2016/11/7.
 */

public class TextConversionBitmapFragment extends BaseFragment {

    @BindView(R.id.image)
    ImageView mImageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_text_conversion_bitmap;
    }

    @Override
    protected void afterInstanceView() {
        String content = "把秋衣扎进秋裤/n把秋裤扎进/n袜子/n是对冬天最起码的/n尊重";
        ImageLoader.getInstance().displayImage("file://" + createTextImage(content, "test.png"), mImageView);
    }

    /**
     * 创建图片
     *
     * @param fileName
     * @return
     */
    public String createTextImage(String content, String fileName) {
        int baseFontSize = 20;//最小的字为20;
        Bitmap bitmap = Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(baseFontSize);
        int y = 6;
        int x = 10;
        int rowLength;
        //换行情况，关系：宽度=字符串长度*字体大小
        if (content.contains("/n")) {
            String temp[] = content.split("/n");
            int maxLength = maxLength(temp);
            //行文字宽度
            rowLength = maxLength * baseFontSize > 200 ? 200 : maxLength * baseFontSize;
            for (int i = 0; i < temp.length; i++) {
                paint.setTextSize(rowLength / temp[i].length());
                if (i == 0) {
                    canvas.drawText(temp[i], x, y + getBaseLine(paint), paint);
                    y = y + getFontHeight(paint) - 3;
                } else {
                    canvas.drawText(temp[i], x, y + getBaseLine(paint), paint);
                    y = y + getFontHeight(paint) - 6;
                }
            }
        } else {
            canvas.drawText(content, x, y + getBaseLine(paint), paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        canvas.restore();// 存储
        return save(bitmap, fileName);
    }

    /**
     * 获取最长的字符串长度
     *
     * @param content
     * @return
     */
    public int maxLength(String[] content) {
        int temp = 0;
        for (int i = 0; i < content.length; i++) {
            if (temp < content[i].length()) {
                temp = content[i].length();
            }
        }
        return temp;
    }

    /**
     * 获取字体高度
     *
     * @param paint
     * @return
     */
    public int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 获取字体baseline线,结合实际显示效果，这里取得粗略值
     * baseline计算http://blog.csdn.net/carrey1989/article/details/10399727
     * http://blog.csdn.net/hursing/article/details/18703599
     *
     * @param paint
     * @return
     */
    public int getBaseLine(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (Math.abs(fm.ascent));
    }

    /**
     * 保存bitmap到外存设备
     *
     * @param bitmap
     * @param fileName
     * @return
     */
    public String save(Bitmap bitmap, String fileName) {
        File file = new File(FileUtil.getStoragePath(), fileName);
        String path = file.getAbsolutePath();
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
                    , Uri.fromFile(file)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
