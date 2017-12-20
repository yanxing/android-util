package com.yanxing.networklibrary.intercepter;

import android.content.Context;

import com.yanxing.networklibrary.util.NetworkStateUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存策略，无网络时使用缓存
 * Created by lishuangxiang on 2016/11/25.
 */
public class CacheInterceptor implements Interceptor {

    private Context mContext;
    private boolean mUseCache;

    /**
     *
     * @param context
     * @param useCache 有网络时，为true使用缓存数据，无网络情况此值无效
     */
    public CacheInterceptor(Context context, boolean useCache) {
        this.mContext = context;
        this.mUseCache=useCache;
    }

    public CacheInterceptor(Context context){
        this.mContext=context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        //无网络，超时时间为1周
        if (!NetworkStateUtil.isNetworkConnected(mContext.getApplicationContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            response = chain.proceed(request);
            int maxStale = 60 * 60 * 24 * 7;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //有网络时
            String cacheControl;
            //使用缓存
            if (mUseCache){
                //使用缓存，参数和无网络参数一样
                int maxStale = 60 * 60 * 24 * 7;
                cacheControl="public, only-if-cached, max-stale=" + maxStale;
            }else {
                cacheControl="public,no-cache";
            }
            response = chain.proceed(request);
            response.newBuilder()
                    .header("Cache-Control",cacheControl)
                     //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
