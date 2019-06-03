package com.yanxing.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


/**
 * APP初始化配置
 * Created by lishuangxiang on 2016/1/26.
 */
public class  MyApplication extends Application {

    private SQLiteDatabase db;
    private static MyApplication mMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initBaiduMap();
        mMyApplication=this;
        Bugly.init(getApplicationContext(), "cb96408e0e", false);
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Beta.installTinker();
    }

    public static Application getInstance() {
        return mMyApplication;
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(getApplicationContext());
    }
}
