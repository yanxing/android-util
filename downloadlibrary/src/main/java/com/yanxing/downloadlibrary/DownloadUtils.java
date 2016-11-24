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
    private DownloadListener mDownloadListener;
    //CPU核心数
    private int CORE_NUM = Runtime.getRuntime().availableProcessors();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(CORE_NUM);
    private DownloadConfiguration mDownloadConfiguration;
    // 记录各线程下载长度
    private Map<Integer, Integer> mData = new ConcurrentHashMap<>();
    private List<DownloadMessage> mDownloadMessageList = new ArrayList<>();
    private DownloadDao mDownloadDao;
    /**
     * 文件总大小
     */
    private int mFileSize;
    /**
     * 已经下载文件长度
     */
    private int mDownloadSize = 0;
    //停止任务标志，true标志任务停止
    private boolean mIsStop;
    private String mUrl;

    private Thread mThread;

    public static DownloadUtils getInstance() {
        return SingleHolder.mDownloadUtils;
    }

    private static class SingleHolder {
        private static final DownloadUtils mDownloadUtils = new DownloadUtils();
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
        if (computeDownloadTask(mUrl)){
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
            int startPosition = mDownloadMessageList.get(index - 1).getStartDownload() +
                    mDownloadMessageList.get(index - 1).getDownloadLength();
            int endPosition = mDownloadMessageList.get(index - 1).getEndDownload();
            //位置和数组下标一样，0到总大小减1
            conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
            conn.connect();
            byte[] buffer = new byte[1024];
            int offset;
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 206) {
                InputStream inStream = conn.getInputStream();
                LogUtil.d("DownloadUtils", Thread.currentThread().getName() + "线程需要下载的文件大小" + (endPosition - startPosition + 1)
                        + "   开始位置" + startPosition + "  结束位置" + endPosition);
                File file = new File(getSavePath() + getFileName(conn, urlPath));
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(startPosition);
                while (!isStop() && (offset = inStream.read(buffer, 0, 1024)) != -1 && mFileSize >= mDownloadSize) {
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
                LogUtil.d("DownloadUtils", "总文件大小mFileSize=" + mFileSize + "    " + Thread.currentThread().getName() +
                        "线程已下载大小" + mData.get(index));
                //所有线程已经下载完
                if (!mIsStop && mDownloadSize > 0 && mFileSize <= mDownloadSize) {
                    mIsStop = true;
                    mDownloadDao.close();
                    mDownloadListener.onFinish();
                    randomAccessFile.close();
                    inStream.close();
                }
            } else {
                LogUtil.d("DownloadUtils", conn.getResponseCode() + "  " + conn.getResponseMessage());
                mDownloadListener.onError(conn.getResponseCode(), conn.getResponseMessage());
            }
        } catch (final Exception e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mDownloadListener.onError(-1,e.getMessage());
                }
            });
            e.printStackTrace();
        }
    }

    /**
     * 删除下载记录,删除数据库记录和本地文件
     *
     * @param context
     * @param url
     */
    public void delete(Context context, String url) {
        DownloadDao downloadDao = new DownloadDao(context);
        List<DownloadMessage> downloadMessageList = downloadDao.getDownloadMessage(url);
        if (downloadMessageList.size() > 0) {
            downloadDao.delete(url);
            File file = new File(downloadMessageList.get(0).getStoragePath());
            file.delete();
        }
    }

    /**
     * 获取已经下载的文件进度，如果没有下载记录返回-1，如果数据库中有下载记录，但是本地文件已经被删除，这时将置数据库中记录
     * 的各个线程的下载长度为0
     *
     * @param context
     * @param url
     * @return -2没有记录，-1下载的文件已删除， 0-100
     */
    public int getDownloadProgressByUrl(Context context, String url) {
        int progress = -2;
        DownloadDao downloadDao = new DownloadDao(context);
        List<DownloadMessage> downloadMessageList = downloadDao.getDownloadMessage(url);
        if (downloadMessageList.size() > 0) {
            File file = new File(downloadMessageList.get(0).getStoragePath());
            //文件已经被破坏
            if (!file.exists()) {
                for (int i = 0; i < downloadMessageList.size(); i++) {
                    DownloadMessage downloadMessage = new DownloadMessage();
                    downloadMessage.setThreadId(++i);
                    downloadMessage.setDownloadLength(0);
                    downloadMessage.setUrl(url);
                    mDownloadDao.update(downloadMessage);
                }
                return -1;
            } else {
                for (int i = 0; i < downloadMessageList.size(); i++) {
                    progress += downloadMessageList.get(i).getDownloadLength();
                }
                int totalSize = downloadMessageList.get(downloadMessageList.size() - 1).getEndDownload() + 1;
                return (int) ((progress * 1.0 / totalSize) * 100);
            }
        }
        return progress;
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
        mDownloadDao.close();
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
     * 考虑到本次的断点续下载传入的保存路径和上次的不一样，这时将重新下载并更新数据库
     */
    private boolean computeDownloadTask(final String urlPath) {
        final boolean[] isSuccess = {true};
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //已经存在此条下载任务
                    if (mDownloadDao.isExist(urlPath)) {
                        mDownloadMessageList = mDownloadDao.getDownloadMessage(urlPath);
                        String path = mDownloadMessageList.get(0).getStoragePath();

                        //断点下载文件保存的路径和上次的不一样，则重新下载，同时更新数据库
                        if (!path.equals(getSavePath().getAbsolutePath())) {
                            for (int i = 1; i <= mDownloadMessageList.size(); i++) {
                                mData.put(i, 0);
                                addDownloadSize(0);
                                DownloadMessage downloadMessage = new DownloadMessage();
                                downloadMessage.setThreadId(i);
                                downloadMessage.setDownloadLength(0);
                                downloadMessage.setUrl(urlPath);
                                mDownloadDao.update(downloadMessage);
                            }
                        } else {
                            for (int i = 1; i <= mDownloadMessageList.size(); i++) {
                                int temp = mDownloadMessageList.get(i - 1).getDownloadLength();
                                mData.put(i, temp);
                                addDownloadSize(temp);
                            }
                        }
                        mFileSize = mDownloadMessageList.get(mDownloadMessageList.size() - 1).getEndDownload() + 1;
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
                                //下标从0开始
                                int startDownload = (i - 1) * block;
                                int endDownload;
                                if (i != CORE_NUM) {
                                    endDownload = i * block - 1;
                                } else {
                                    //如果不平均，最后一个线程下载量加上剩余的
                                    endDownload = i * block - 1 + mFileSize % CORE_NUM;
                                }
                                DownloadMessage downloadMessage1 = new DownloadMessage(i, 0, startDownload, endDownload
                                        , getSavePath().getAbsolutePath(), urlPath);
                                mDownloadMessageList.add(downloadMessage1);
                                mData.put(i, 0);
                            }
                            mDownloadDao.save(mDownloadMessageList);
                        } else {
                            mDownloadListener.onError(conn.getResponseCode(), conn.getResponseMessage());
                        }
                    }
                } catch (final Exception e) {
                    isSuccess[0] =false;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onError(-1,e.getMessage());
                        }
                    });
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
        return isSuccess[0];
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
