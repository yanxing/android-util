package com.yanxing.util;

import com.facebook.common.util.ByteConstants;

/**
 * 常量
 * Created by lishuangxiang on 2016/1/26.
 */
public class ConstantValue {

    /**
     * 缓存图片文件夹
     */
    public static final String CACHE_IMAGE="yanxing/image/";

    /**
     * imageloader、fresco加载本地图片
     */
    public static final String FILE_CACHE_IMAGE="file://"+FileUtil.getStoragePath()+CACHE_IMAGE;

    /*--------------------------------------------------------------fresco配置---------------------------------------------------------*/
    /**
     * 默认图极低磁盘空间缓存的最大值
     */
    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;
    /**
     * 默认图低磁盘空间缓存的最大值
     */
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;
    /**
     * 默认图磁盘缓存的最大值
     */
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;

    /**
     * 缩略图图片 http://ww1.sinaimg.cn/thumbnail/8c9ef2e9gw1fbcz48b05gj20u00u0dk2.jpg
     */
    public static final String THUMBNAIL_PIC="thumbnail";
    /**
     * 中等图片 http://ww1.sinaimg.cn/thumbnail/8c9ef2e9gw1fbcz48b05gj20u00u0dk2.jpg
     * 替换thumbnail获取的则为中等图片
     */
    public static final String BMIDDLE_PIC="bmiddle";
    /**
     * 原图 http://ww1.sinaimg.cn/thumbnail/8c9ef2e9gw1fbcz48b05gj20u00u0dk2.jpg
     * 替换thumbnail获取的则为原图
     */
    public static final String ORIGINAL_PIC="large";

}
