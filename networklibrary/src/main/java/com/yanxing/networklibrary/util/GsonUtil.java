package com.yanxing.networklibrary.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author 李双祥 on 2018/8/16.
 */
public class GsonUtil {

    /**
     * 过滤解析带有注解@Expose属性
     * @return
     */
    public static Gson createGson() {
        return new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes arg0) {
                        Expose expose = arg0.getAnnotation(Expose.class);
                        if (expose != null && expose.deserialize()) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> arg0) {
                        return false;
                    }
                }).create();
    }
}
