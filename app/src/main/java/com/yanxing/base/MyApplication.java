package com.yanxing.base;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;



/**
 * APP初始化配置
 * Created by lishuangxiang on 2016/1/26.
 */
public class  MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
