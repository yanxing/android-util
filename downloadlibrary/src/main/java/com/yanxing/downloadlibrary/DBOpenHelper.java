package com.yanxing.downloadlibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建，记录线程下载信息
 * Created by lishuangxiang on 2016/9/20.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "download.db";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //threadId线程索引，url文件下载地址，downloadLength已经下载的长度，
        //[startDownload,endDownload]下载的开始位置和结束位置,流从0开始
        //storagePath 本地存储的路径
        //threadId，url联合主键
        String sql = "create table if not exists file_download" +
                "(threadId integer,url varchar(100),downloadLength integer," +
                "startDownload integer,endDownload integer,storagePath varchar(100),PRIMARY KEY(threadId,url))";
        sqLiteDatabase.execSQL(sql);
    }

    // 更新数据库，当版本变化时系统会调用该回调方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists file_download";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
