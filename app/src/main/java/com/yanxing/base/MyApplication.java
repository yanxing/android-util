package com.yanxing.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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
        initImageLoader();
        initBaiduMap();
        initFresco();
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
     * 初始化fresco
     */
    private  void initFresco(){
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        File file=new File(FileUtil.getStoragePath());
        //自定义图片的磁盘配置,fresco缓存文件后缀cnt
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(file)
                .setBaseDirectoryName(ConstantValue.CACHE_IMAGE)
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(getApplicationContext(),imagePipelineConfig);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(getApplicationContext());
    }

    /**
     * 初始化UIL配置
     *
     */
    public void initImageLoader() {
        File file =new File(FileUtil.getStoragePath()+ConstantValue.CACHE_IMAGE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
