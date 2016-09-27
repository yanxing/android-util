package com.yanxing.downloadlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
 * 下载
 * Created by lishuangxiang on 2016/9/19.
 */
public class DownloadUtils {
    private static DownloadUtils mOurInstance = new DownloadUtils();
    private DownloadListener mDownloadListener;
    //CPU核心数
    private int CORE_NUM = Runtime.getRuntime().availableProcessors();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(CORE_NUM);
    private DownloadConfiguration mDownloadConfiguration;
    // 记录各线程下载长度
    private static Map<Integer, Integer> mData = new ConcurrentHashMap<>();
    private static List<DownloadMessage> mDownloadMessageList=new ArrayList<>();
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
    }

    /**
     * 根据url下载文件
     *
     * @param url
     * @param downloadListener 下载监听
     */
    public void startDownload(Context context,String url, DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
        this.mDownloadListener.onStart();
        this.mUrl = url;
        mDownloadDao = new DownloadDao(context);
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
            //没有记录
            synchronized (mDownloadMessageList){
                if (mDownloadMessageList.size()==0){
                    conn.connect();
                    mFileSize=conn.getContentLength();
                    Log.d("DownloadUtils","文件总大小"+mFileSize/1024.0/1024+"兆");
                    computeDownloadTask(urlPath,index);
                }
            }
            int startPosition = mDownloadMessageList.get(index-1).getStartDownload();
            int endPosition = mDownloadMessageList.get(index-1).getEndDownload();
            conn.disconnect();
            conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
            conn.connect();
            InputStream inStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int offset;
            if (conn.getResponseCode() == 200) {
                Log.d("DownloadUtils","第"+index+"此线程下载的文件大小"+(endPosition-startPosition+1)/1024.0/1024+"兆");
                File file = new File(getSavePath() + getFileName(conn,urlPath));
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(startPosition);
                while (!isStop() && (offset = inStream.read(buffer, 0, 1024)) != -1) {
                    randomAccessFile.write(buffer, 0, offset);
                    mData.put(index, mData.get(index) + offset);
                    updateData(index, mData.get(index), urlPath);
                    addDownloadSize(offset);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onProgress(mDownloadSize,mFileSize);
                        }
                    });
                }
                if (mFileSize<=mDownloadSize){
                    mDownloadDao.delete(urlPath);
                    mDownloadDao.close();
                    mDownloadListener.onFinish();
                }
                randomAccessFile.close();
                inStream.close();
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
            throw new NullPointerException("url is not null,you should call startDownload method");
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
     * 计算每个线程需要下载的文件长度，并保存到数据库，同一threadId和url不会被保存
     * 如果已经存在，则不作修改，每条线程已经下载的长度保存到变量mData中
     */
    private void computeDownloadTask(String urlPath,int index) {
        DownloadMessage downloadMessage = new DownloadMessage();
        downloadMessage.setUrl(urlPath);
        downloadMessage.setThreadId(index);
        //已经存在此条下载记录
        if (mDownloadDao.isExist(downloadMessage)) {
            mDownloadMessageList= mDownloadDao.getDownloadMessage(urlPath);
            for (int i = 1; i <= mDownloadMessageList.size(); i++) {
                mData.put(i, mDownloadMessageList.get(i-1).getDownloadLength());
            }
        } else {
            int block = mFileSize / CORE_NUM;
            for (int i = 1; i <= CORE_NUM; i++) {
                int startDownload = (i - 1) * block + 1;
                int endDownload;
                if (i != CORE_NUM) {
                    endDownload = i * block;
                } else {
                    endDownload = i * block + mFileSize % CORE_NUM;
                }
                DownloadMessage downloadMessage1 = new DownloadMessage(i, 0, startDownload, endDownload, urlPath);
                mDownloadMessageList.add(downloadMessage1);
                mData.put(i, 0);
            }
            mDownloadDao.save(mDownloadMessageList);
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
    private String getFileName(HttpURLConnection conn,String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        if (fileName.isEmpty()||!fileName.contains(".")){
            for (int i = 0;; i++) {
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
