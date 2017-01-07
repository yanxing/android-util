package com.yanxing.util;

import android.os.Handler;
import android.os.Looper;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载图片(png,gif,jpg)
 * Created by lishuangxiang on 2016/11/24.
 */
public class DownloadImageUtil {

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private DownloadListener mDownloadListener;

    public static DownloadImageUtil getInstance() {
        return SingleHolder.mDownloadUtils;
    }

    private static class SingleHolder {
        private final static DownloadImageUtil mDownloadUtils
                = new DownloadImageUtil();
    }

    private DownloadImageUtil() {
    }

    /**
     * 下载图片
     *
     * @param url              图片路径
     * @param savePath         图片保存路径
     * @param downloadListener
     */
    public void downloadImage(final String url, final String savePath, DownloadListener downloadListener) {
        WeakReference<DownloadListener> weakReference = new WeakReference<DownloadListener>(downloadListener);
        this.mDownloadListener = weakReference.get();
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        String fileType = conn.getContentType();
                        String imageName = String.valueOf(System.currentTimeMillis());
                        if (fileType.equalsIgnoreCase("image/gif")) {
                            imageName = imageName + ".gif";
                        } else if (fileType.equalsIgnoreCase("image/jpeg")) {
                            imageName = imageName + ".jpg";
                        } else if (fileType.equalsIgnoreCase("image/png")) {
                            imageName = imageName + ".png";
                        } else {
                            if (mDownloadListener != null) {
                                mDownloadListener.error("url is not image");
                            }
                            return;
                        }
                        InputStream inStream = conn.getInputStream();
                        FileUtil.writeStInput(savePath, imageName, inStream);
                        final String finalImageName = imageName;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (mDownloadListener != null) {
                                    mDownloadListener.success(savePath + finalImageName);
                                }
                            }
                        });
                    } else {
                        if (mDownloadListener != null) {
                            mDownloadListener.error(conn.getResponseMessage());
                        }
                    }
                } catch (Exception e) {
                    if (mDownloadListener != null) {
                        mDownloadListener.error(e.getMessage());
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public interface DownloadListener {
        /**
         * @param path 下载成功保存的路径
         */
        void success(String path);

        void error(String message);
    }
}
