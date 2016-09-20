package com.yanxing.downloadlibrary;

/**
 * 下载监听
 * Created by lishuangxiang on 2016/9/19.
 */
public interface DownloadListener {

    /**
     * 下载之前
     */
    void onBefore();

    /**
     * 下载进度
     */
    void onProgress(int progress);

    /**
     * 下载完成
     */
    void onAfter();
}
