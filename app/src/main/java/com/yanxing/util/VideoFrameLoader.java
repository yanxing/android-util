package com.yanxing.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.LruCache;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 加载视频某一帧工具类
 * Created by lishuangxiang on 2016/10/11.
 */

public class VideoFrameLoader {

    private static VideoFrameLoader mVideoFrameLoader = new VideoFrameLoader();
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruCache mDiskLruCache;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors());

    /**
     * 磁盘最大缓存
     */
    private static final long MAX_SIZE = 20 * 1024 * 1024;

    public static VideoFrameLoader getInstance() {
        return mVideoFrameLoader;
    }

    private VideoFrameLoader() {
    }

    /**
     * 初始化
     * @param file  缓存目录
     * @param appVersion  app版本号
     */
    public void initImageCache(File file, int appVersion) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一可用内存作为缓存
        int cacheSize = maxMemory / 4;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
        try {
            mDiskLruCache = DiskLruCache.open(file, appVersion,1, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示视频某一帧
     *
     * @param url
     * @param imageView
     */
    public void displayVideoFrame(final String url, final ImageView imageView) {
        final WeakReference<ImageView> viewRef = new WeakReference<>(imageView);
        //检查内存是否有缓存
        Bitmap bitmap = mLruCache.get(url);
        if (bitmap != null) {
            viewRef.get().setImageBitmap(bitmap);
            return;
        }

        //检查磁盘是否有缓存
        String key = getStringByMD5(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap diskBitmap = BitmapFactory.decodeStream(inputStream);
                viewRef.get().setImageBitmap(diskBitmap);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //重新下载
        viewRef.get().setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getFrameAtTime(url);
                if (bitmap == null) {
                    return;
                }
                if (viewRef.get().getTag().equals(url)) {
                    viewRef.get().setImageBitmap(bitmap);
                }
                mLruCache.put(url, bitmap);
                try {
                    String key = getStringByMD5(url);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        editor.commit();
                        mDiskLruCache.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取视频缩略图
     * @param path
     * @return
     */
    public static Bitmap getFrameAtTime(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path, new HashMap<String, String>());
        return retriever.getFrameAtTime();
    }

    /**
     * 获取字符串的MD5编码.
     */
    public static String getStringByMD5(String string) {
        String md5String = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            byte messageDigestByteArray[] = messageDigest.digest();
            if (messageDigestByteArray == null || messageDigestByteArray.length == 0) {
                return md5String;
            }
            StringBuilder hexadecimalStringBuffer = new StringBuilder();
            int length = messageDigestByteArray.length;
            for (int i = 0; i < length; i++) {
                hexadecimalStringBuffer.append(Integer.toHexString(0xFF & messageDigestByteArray[i]));
            }
            md5String = hexadecimalStringBuffer.toString();
            return md5String;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5String;
    }
}
