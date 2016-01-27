package com.yanxing.base;

import android.app.Application;
import android.content.Context;

import com.baidu.map.BaiduLoc;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.ImageNameGenerator;

import java.io.File;

/**
 * Created by lishuangxiang on 2016/1/26.
 */
public class MyApplication extends Application {

    public static BaiduLoc baiduLoc;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        initImageLoader(getApplicationContext());
        initBaiduMap();
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(this);
        baiduLoc = new BaiduLoc(getApplicationContext());
        baiduLoc.startLocation();
    }

    /**
     * 初始化UIL全局配置
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        File file = FileUtil.createDir(ConstantValue.CACHE_IMAGE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(file, file, new ImageNameGenerator())) // 缓存到SD卡
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
