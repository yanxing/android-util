package com.yanxing.downloadlibrary.model;

/**
 * 线程下载信息
 * Created by lishuangxiang on 2016/9/20.
 */
public class DownloadMessage {

    /**
     * threadId线程索引
     */
    private int threadId;
    /**
     * url文件下载地址
     */
    private String url;
    /**
     * 已经下载的长度
     */
    private int downloadLength;
    /**
     * 下载的开始位置 []，从0开始
     */
    private int startDownload;
    /**
     * 下载的结束位置
     */
    private int endDownload;

    /**
     * 本地存储的路径
     */
    private String storagePath;

    public DownloadMessage() {
    }

    /**
     * @param threadId       threadId线程索引
     * @param downloadLength 已经下载的长度
     * @param startDownload  下载的开始位置 []
     * @param endDownload    下载的结束位置
     * @param url            url文件下载地址
     */
    public DownloadMessage(int threadId, int downloadLength, int startDownload, int endDownload,String storagePath, String url) {
        this.downloadLength = downloadLength;
        this.endDownload = endDownload;
        this.startDownload = startDownload;
        this.threadId = threadId;
        this.storagePath=storagePath;
        this.url = url;
    }

    public int getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(int downloadLength) {
        this.downloadLength = downloadLength;
    }

    public int getEndDownload() {
        return endDownload;
    }

    public void setEndDownload(int endDownload) {
        this.endDownload = endDownload;
    }

    public int getStartDownload() {
        return startDownload;
    }

    public void setStartDownload(int startDownload) {
        this.startDownload = startDownload;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
