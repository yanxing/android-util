package com.yanxing.downloadlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yanxing.downloadlibrary.model.DownloadMessage;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多线程断点下载
 * Created by lishuangxiang on 2016/9/19.
 */
public class DownloadUtils {
    private static DownloadUtils mOurInstance = new DownloadUtils();
    private DownloadListener mDownloadListener;
    //CPU核心数
//    private int CORE_NUM = Runtime.getRuntime().availableProcessors();
    private int CORE_NUM = 6;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(CORE_NUM);
    private DownloadConfiguration mDownloadConfiguration;
    // 记录各线程下载长度
    private static Map<Integer, Integer> mData = new ConcurrentHashMap<>();
    private static List<DownloadMessage> mDownloadMessageList = new ArrayList<>();
    private DownloadDao mDownloadDao;
    /**
     * 文件总大小
     */
    private static int mFileSize;
    /**
     * 已经下载文件长度
     */
    private static int mDownloadSize = 0;
    //停止任务标志，true标志任务停止
    private static boolean mIsStop;
    private String mUrl;

    private Thread mThread;

    public static DownloadUtils getInstance() {
        return mOurInstance;
    }

    private DownloadUtils() {
    }

    /**
     * 初始配置
     *
     * @param downloadConfiguration
     */
    public synchronized void init(DownloadConfiguration downloadConfiguration) {
        if (downloadConfiguration == null) {
            throw new IllegalArgumentException("downloadConfiguration argument must be not null");
        }
        this.mDownloadConfiguration = downloadConfiguration;
        LogUtil.setmAllow(mDownloadConfiguration.isLog());
    }

    /**
     * 根据url下载文件
     *
     * @param url
     * @param downloadListener 下载监听
     */
    public void startDownload(Context context, String url, DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
        this.mDownloadListener.onStart();
        this.mUrl = url;
        mDownloadDao = new DownloadDao(context);
        computeDownloadTask(mUrl);
        for (int i = 1; i <= CORE_NUM; i++) {
            final int finalI = i;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    download(mUrl, finalI);
                }
            });
        }
    }

    /**
     * 下载
     *
     * @param urlPath
     * @param index
     */
    private void download(String urlPath, int index) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");
            int startPosition = mDownloadMessageList.get(index - 1).getStartDownload();
            int endPosition = mDownloadMessageList.get(index - 1).getEndDownload();
            if (index==4){
                conn.setRequestProperty("Range", "bytes=" + startPosition + "-");
            }else {
                conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
            }
            conn.connect();
            InputStream inStream = conn.getInputStream();
            byte[] buffer = new byte[8898715];
            int offset;
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 206) {
                LogUtil.d("DownloadUtils", Thread.currentThread().getName() + "线程需要下载的文件大小" + (endPosition - startPosition + 1)
                +"   开始位置"+startPosition+"  结束位置"+endPosition);
                File file = new File(getSavePath() + getFileName(conn, urlPath));
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(startPosition);
                while (!isStop() && (offset = inStream.read(buffer)) != -1 && mFileSize >=mDownloadSize) {
//                    LogUtil.d("DownloadUtils","线程"+Thread.currentThread().getName()+"offset="+offset);
                    randomAccessFile.write(buffer, 0, offset);
                    mData.put(index, mData.get(index) + offset);
                    updateData(index, mData.get(index), urlPath);
                    addDownloadSize(offset);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onProgress(mDownloadSize, mFileSize);
                        }
                    });
                }
                LogUtil.d("DownloadUtils","状态码"+conn.getResponseCode()+" inStream.read(buffer))="+inStream.read(buffer)+
                        "   当前线程"+Thread.currentThread().getName());

                LogUtil.d("DownloadUtils", "总文件大小mFileSize=" + mFileSize + "    "+Thread.currentThread().getName()+"线程已下载大小" + mData.get(index));
                //所有线程已经下载完
//                if (!mIsStop&&mDownloadSize>0&&mFileSize<=mDownloadSize){
//                    mIsStop = true;
//                    mDownloadDao.delete(urlPath);
//                    mDownloadDao.close();
//                    mDownloadListener.onFinish();
//                    randomAccessFile.close();
//                    inStream.close();
//                }
            } else {
                LogUtil.d("DownloadUtils",conn.getResponseCode()+"  "+conn.getResponseMessage());
                mDownloadListener.onError(conn.getResponseCode(),conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新下载数据
     *
     * @param threadId
     * @param downloadLength
     * @param url
     */
    private synchronized void updateData(int threadId, int downloadLength, String url) {
        DownloadMessage downloadMessage = new DownloadMessage();
        downloadMessage.setThreadId(threadId);
        downloadMessage.setDownloadLength(downloadLength);
        downloadMessage.setUrl(url);
        mDownloadDao.update(downloadMessage);
    }

    /**
     * 恢复下载
     */
    public void resumeDownload() {
        if (mUrl == null) {
            throw new NullPointerException("url is null,you should call startDownload method");
        }
        mIsStop = false;
        for (int i = 1; i <= CORE_NUM; i++) {
            final int finalI = i;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    download(mUrl, finalI);
                }
            });
        }
    }

    /**
     * 停止下载
     */
    public void stopDownload() {
        mIsStop = true;
    }

    /**
     * 获取下载状态，true为停止下载
     *
     * @return
     */
    private boolean isStop() {
        return mIsStop;
    }

    /**
     * 计算每个线程需要下载的文件长度，并保存到数据库，
     * 如果数据库已经存在，则不作修改，每条线程已经下载的长度保存到变量mData中
     */
    private void computeDownloadTask(final String urlPath) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DownloadMessage downloadMessage = new DownloadMessage();
                    downloadMessage.setUrl(urlPath);
                    //已经存在此条下载任务
                    if (mDownloadDao.isExist(downloadMessage)) {
                        mDownloadMessageList = mDownloadDao.getDownloadMessage(urlPath);
                        for (int i = 1; i <= mDownloadMessageList.size(); i++) {
                            mData.put(i, mDownloadMessageList.get(i - 1).getDownloadLength());
                        }
                    } else {
                        URL url = new URL(urlPath);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Charset", "UTF-8");
                        conn.connect();
                        if (conn.getResponseCode() == 200) {
                            mFileSize = conn.getContentLength();
                            int block = mFileSize / CORE_NUM;
                            for (int i = 1; i <= CORE_NUM; i++) {
                                int startDownload = (i - 1) * block + 1;
                                int endDownload;
                                if (i != CORE_NUM) {
                                    endDownload = i * block;
                                } else {
                                    endDownload = i * block + mFileSize % CORE_NUM;//不平均，最后一个线程下载加上剩余的
                                }
                                DownloadMessage downloadMessage1 = new DownloadMessage(i, 0, startDownload, endDownload, urlPath);
                                mDownloadMessageList.add(downloadMessage1);
                                mData.put(i, 0);
                            }
                            mDownloadDao.save(mDownloadMessageList);
                        } else {
                            mDownloadListener.onError(conn.getResponseCode(),conn.getResponseMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 累计已下载大小
     *
     * @param size
     */
    private synchronized void addDownloadSize(int size) {
        mDownloadSize += size;
    }

    /**
     * 获取文件名,从下载路径的字符串中获取文件名称
     */
    private String getFileName(HttpURLConnection conn, String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        if (fileName.isEmpty() || !fileName.contains(".")) {
            for (int i = 0; ; i++) {
                String mine = conn.getHeaderField(i);// 从返回的流中获取特定索引的头字段值
                if (mine == null)
                    break;
                if ("content-disposition".equals(conn.getHeaderField(i)
                        .toLowerCase())) {
                    Matcher matcher = Pattern.compile(".*fileName=(.*)")
                            .matcher(mine.toLowerCase());
                    if (matcher.find()) {
                        return matcher.group();
                    }
                }
            }
        }
        return fileName;
    }

    /**
     * 获取存储路径
     *
     * @return
     */
    private File getSavePath() {
        return mDownloadConfiguration.getSavePath();
    }
}
