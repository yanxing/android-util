package com.yanxing.base;

import android.app.Application;
import android.content.Context;

import com.baidu.map.BaiduLoc;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yanxing.util.ConstantValue;
import com.yanxing.util.FileUtil;
import com.yanxing.util.ImageNameGenerator;

import java.io.File;

/**
 * APP初始化配置
 * Created by lishuangxiang on 2016/1/26.
 */
public class MyApplication extends Application {

    public static BaiduLoc baiduLoc;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initBaiduMap();
        initFresco();
    }

    /**
     * 初始化fresco
     */
    private  void initFresco(){
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
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
                .setProgressiveJpegConfig(pjpegConfig)
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
}
