package com.yanxing.downloadlibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yanxing.downloadlibrary.model.DownloadMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 * Created by lishuangxiang on 2016/9/20.
 */
public class DownloadDao {

    private DBOpenHelper mDBOpenHelper;
    private SQLiteDatabase db;
    private Cursor mCursor;

    public DownloadDao(Context context) {
        mDBOpenHelper = new DBOpenHelper(context);
        db = mDBOpenHelper.getWritableDatabase();
    }

    /**
     * 添加一条下载记录
     */
    public void save(DownloadMessage downloadMessage) {
        ContentValues values = new ContentValues();
        values.put("threadId", downloadMessage.getThreadId());
        values.put("url", downloadMessage.getUrl());
        values.put("downloadLength", downloadMessage.getDownloadLength());
        values.put("startDownload", downloadMessage.getStartDownload());
        values.put("endDownload", downloadMessage.getEndDownload());
        db.insert("file_download", null, values);
    }

    /**
     * 事务提交一组下载记录
     *
     * @param messageList
     */
    public void save(List<DownloadMessage> messageList) {
        db.beginTransaction();
        try {
            for (DownloadMessage downloadMessage : messageList) {
                db.execSQL(
                        "insert into file_download(threadId,url,downloadLength,startDownload," +
                                "endDownload,storagePath) values(?,?,?,?,?,?)",
                        new Object[]{downloadMessage.getThreadId(), downloadMessage.getUrl()
                                , downloadMessage.getDownloadLength(), downloadMessage.getStartDownload()
                                , downloadMessage.getEndDownload(),downloadMessage.getStoragePath()});
            }
            db.setTransactionSuccessful();// 事务执行成功的标志
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        } finally {
            db.endTransaction();// 结束一个事务，如果事务设置了成功标志，则提交事务，否则会回滚事务
        }
    }

    /**
     * url相同认为同一条下载任务
     *
     * @param url
     * @return
     */
    public boolean isExist(String url) {
        mCursor = db.rawQuery("select * from file_download where url=?"
                , new String[]{url});
        return mCursor.moveToNext();
    }

    /**
     * 根据url获取已经下载的文件长度
     *
     * @param url
     * @return 返回此url各个线程下载集合
     */
    public synchronized List<DownloadMessage> getDownloadMessage(String url) {
        mCursor = db.rawQuery("select * from file_download where url=?", new String[]{url});
        List<DownloadMessage> list = new ArrayList<DownloadMessage>();
        while (mCursor.moveToNext()) {
            DownloadMessage downloadMessage = new DownloadMessage();
            downloadMessage.setThreadId(mCursor.getInt(0));
            downloadMessage.setUrl(mCursor.getString(1));
            downloadMessage.setDownloadLength(mCursor.getInt(2));
            downloadMessage.setStartDownload(mCursor.getInt(3));
            downloadMessage.setEndDownload(mCursor.getInt(4));
            downloadMessage.setStoragePath(mCursor.getString(5));
            list.add(downloadMessage);
        }
        return list;
    }

    /**
     * 更新，只更新已下载长度
     *
     * @param downloadMessage
     */
    public void update(DownloadMessage downloadMessage) {
        ContentValues values = new ContentValues();
        values.put("downloadLength", downloadMessage.getDownloadLength());
        db.update("file_download", values, "threadId=? and url=?", new String[]{
                String.valueOf(downloadMessage.getThreadId()), String.valueOf(downloadMessage.getUrl())});
    }


    /**
     * 删除此url的记录
     *
     * @param url
     */
    public void delete(String url) {
        if (isExist(url)){
            db.delete("file_download", "url=?", new String[]{url});
        }
    }


    /**
     * 关闭数据库连接
     */
    public void close() {
        if (mDBOpenHelper != null) {
            mDBOpenHelper.close();
            mDBOpenHelper = null;
        }
        if (db != null) {
            db.close();
            db = null;
        }
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }
}
