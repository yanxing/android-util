package com.yanxing.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 压缩图片
 * Created by lishuangxiang on 2016/1/25.
 */
public class BitmapUtil {
    /**
     * 压缩图片，生成新的图片,仅用于图片上传
     *
     * @param filePath  图片原路径
     * @param temp      压缩图片路径
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static String compressFile(String filePath, String temp, int reqWidth, int reqHeight) {

        Bitmap bm = getSmallBitmap(filePath, reqWidth, reqHeight);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(temp);//写入的文件路径
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bm == null) {
            return temp;
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 80, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize < 1 ? 1 : inSampleSize;
    }

    /**
     * 根据图片路径返回bitmap
     *
     * @param filePath
     * @return
     */
    private static Bitmap getSmallBitmap(String filePath, int newWidth, int newHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不返回实际的bitmap对象内存
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
}
