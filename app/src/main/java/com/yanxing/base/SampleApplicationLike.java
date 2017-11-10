package com.yanxing.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.baidu.map.BaiduLoc;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.yanxing.dao.DaoMaster;
import com.yanxing.dao.DaoSession;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 李双祥 on 2017/11/10.
 */
public class SampleApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker.SampleApplicationLike";
    private BaiduLoc mBaiduLoc;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;
    private static SampleApplicationLike mApplication;
    private Context mContext;

    public SampleApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplication(), "cb96408e0e", true);
        mApplication=this;
        mContext=mApplication.getApplication().getApplicationContext();
        initImageLoader();
        initBaiduMap();
        initFresco();
        initGreen();
        //内存泄漏检测
//        LeakCanary.install(this);
        Stetho.initializeWithDefaults(getApplication().getApplicationContext());;

    }

    public static SampleApplicationLike getInstance() {
        return mApplication;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
    /**
     * 初始化GreenDao
     */
    public void initGreen(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "yanxing-db", null);
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
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        File file=new File(FileUtil.getStoragePath());
        //自定义图片的磁盘配置,fresco缓存文件后缀cnt
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(mContext)
                .setBaseDirectoryPath(file)//缓存图片基路径
                .setBaseDirectoryName(ConstantValue.CACHE_IMAGE)//文件夹名
                .setMaxCacheSize(ConstantValue.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(ConstantValue.MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(ConstantValue.MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(mContext)
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(mContext,imagePipelineConfig);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(mContext);
        mBaiduLoc = new BaiduLoc(mContext);
    }

    /**
     * 初始化UIL配置
     *
     */
    public void initImageLoader() {
        File file =new File(FileUtil.getStoragePath()+ConstantValue.CACHE_IMAGE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public BaiduLoc getBaiduLoc() {
        return mBaiduLoc;
    }

    public void setBaiduLoc(BaiduLoc baiduLoc) {
        this.mBaiduLoc = baiduLoc;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
