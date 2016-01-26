package com.yanxing.util;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;

/**
 * 自定义Universal Image Loader缓存图片命名规则
 * Created by lishuangxiang on 2016/1/25.
 */
public class ImageNameGenerator implements FileNameGenerator {

    /**
     * 使用图片原名称去掉后缀名，加上".0"命名缓存图片
     * 例如：http://bangmang.la/test.png，缓存的文件为test.0
     * @param imageUri
     * @return
     */
    @Override
    public String generate(String imageUri) {
        String image[]=imageUri.split("/");
        return image[image.length-1].split("\\.")[0]+".0";
    }
}
