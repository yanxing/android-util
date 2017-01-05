package com.yanxing.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.map.BaiduLoc;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;
import com.yanxing.dao.DaoMaster;
import com.yanxing.dao.DaoSession;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;

import java.io.File;

/**
 * APP初始化配置
 * Created by lishuangxiang on 2016/1/26.
 */
public class  MyApplication extends Application {

    public  BaiduLoc baiduLoc;

    private SQLiteDatabase db;

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initBaiduMap();
        initFresco();
        initGreen();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 初始化GreenDao
     */
    public void initGreen(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "yanxing-db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 初始化fresco
     */
    private  void initFresco(){
        File file=new File(FileUtil.getStoragePath());
        //自定义图片的磁盘配置,fresco缓存文件后缀cnt
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(file)//缓存图片基路径
                .setBaseDirectoryName(ConstantValue.CACHE_IMAGE)//文件夹名
                .setMaxCacheSize(ConstantValue.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(ConstantValue.MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(ConstantValue.MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置
                .build();
        Fresco.initialize(getApplicationContext(),imagePipelineConfig);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(this);
        baiduLoc = new BaiduLoc(getApplicationContext());
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
//                .diskCache(new UnlimitedDiskCache(file, file, new ImageNameGenerator())) // 自定义命名规则缓存到外存
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public BaiduLoc getBaiduLoc() {
        return baiduLoc;
    }

    public void setBaiduLoc(BaiduLoc baiduLoc) {
        this.baiduLoc = baiduLoc;
    }
}
