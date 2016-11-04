package com.yanxing.downloadlibrary;

/**
 * 下载监听
 * Created by lishuangxiang on 2016/9/19.
 */
public interface DownloadListener {

    /**
     * 下载之前
     */
    void onStart();

    /**
     * 下载进度
     *
     * @param progress  当前下载大小
     * @param totalSize 文件总大小
     */
    void onProgress(int progress, int totalSize);

    /**
     * 下载完成
     */
    void onFinish();

    /**
     * 下载错误
     *
     * @param state 返回码 -1为自定义错误码，代表网络错误
     * @param message 错误信息
     */
    void onError(int state,String message);
}
