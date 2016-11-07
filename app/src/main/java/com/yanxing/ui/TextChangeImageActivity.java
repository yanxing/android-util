package com.yanxing.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;

/**
 * 文字转化为图片，宽度一致，绘画
 * Created by lishuangxiang on 2016/11/7.
 */

public class TextChangeImageActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mImageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_text_change_image;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(true, this);
        String content = "把秋衣扎进秋裤/n把秋裤扎进/n袜子/n是对冬天最起码的/n尊重";
        ImageLoader.getInstance().displayImage("file://"+createTextImage(content,"test.png"),mImageView);
    }

    /**
     * 创建图片
     *
     * @param fileName
     * @return
     */
    public String createTextImage(String content,String fileName) {
        int baseFontSize=20;//最小的字为20，为基数;
        Bitmap bitmap = Bitmap.createBitmap(200,400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
//        paint.setFilterBitmap(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(baseFontSize);
//        String temp[] = content.split("/n");

        //包含换行   关系：字符串长度*字体大小=宽度
        if (content.contains("/n")) {
            String temp[] = content.split("/n");
            int maxLength=maxLength(temp);
            //行文字宽度
            int row=maxLength*baseFontSize>200?200:maxLength*baseFontSize;
            int y=20;
            for (int i = 0; i < temp.length; i++) {
                paint.setTextSize(row/temp[i].length());
                if (i == 0) {
                    LogUtil.d(TAG,y+"");
                    canvas.drawText(temp[i], 10, y, paint);
                    y=y+getFontHeight(row/temp[i].length());
                } else {
//                    int yy=y+getFontHeight(row/temp[i].length())+y;
                    LogUtil.d(TAG,y+"");

                    canvas.drawText(temp[i], 10, y+getBaseLine(paint), paint);
                    y=y+getFontHeight(row/temp[i].length());


                }
            }
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        canvas.restore();// 存储
        return save(bitmap,fileName);
    }

    /**
     * 获取最长的字符串长度
     * @param content
     * @return
     */
    public int maxLength(String[] content){
        int temp=0;
        for (int i=0;i<content.length;i++){
            if (temp<content[i].length()){
                temp=content[i].length();
            }
        }
        return temp;
    }

    /**
     * 获取字体高度
     * @param textSize
     * @return
     */
    public int getFontHeight(float textSize)
    {
        Paint paint=new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 获取字体baseline线
     * @param paint
     * @return
     */
    public int getBaseLine(Paint paint)
    {
        Paint.FontMetrics fm = paint.getFontMetrics();
//        return (int) Math.abs((fm.ascent-fm.descent)/2 + (fm.descent - fm.ascent)/2 - fm.descent);
        return (int)((fm.bottom-fm.top)/2-fm.bottom+6);
    }

    public String save(Bitmap bitmap,String fileName) {
        File file = new File(FileUtil.getStoragePath(),fileName);
        String path=file.getAbsolutePath();
        try {
            // 文件夹不存在则创建
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(file)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

}
