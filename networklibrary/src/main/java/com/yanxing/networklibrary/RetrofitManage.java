package com.yanxing.networklibrary;


import android.content.Context;

import com.yanxing.networklibrary.intercepter.CacheInterceptor;
import com.yanxing.networklibrary.intercepter.ParameterInterceptor;
import com.yanxing.networklibrary.intercepter.Interceptor;
import com.yanxing.networklibrary.util.GsonUtil;
import com.yanxing.networklibrary.util.LogUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lishuangxiang on 2017/04/01.
 */
public class RetrofitManage {

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpClientBuilder;

    private RetrofitManage() {
    }

    public static RetrofitManage getInstance() {
        return SingletonHolder.retrofitManage;

    }

    private static class SingletonHolder {
        private static final RetrofitManage retrofitManage = new RetrofitManage();
    }

    /**
     * 初始化Retrofit
     *
     * @param baseUrl
     * @param log     true打印请求参数和返回数据
     */
    public synchronized void init(String baseUrl, boolean log) {
        LogUtil.isDebug = log;
        mOkHttpClientBuilder = getOkHttpClientBuilderTimeout()
                .addInterceptor(new ParameterInterceptor());
        mRetrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClientBuilder.build());
    }

    /**
     * 设置拦截器
     */
    public void setInterceptor(Interceptor interceptor) {
        mRetrofitBuilder.client(mOkHttpClientBuilder.addInterceptor(interceptor).build());
    }

    /**
     * 为每个请求设置头部信息，比如token信息，这里重新创建OkHttpClient.Builder()，防止之前添加的拦截器（更新header）再次执行
     *
     * @param headers
     */
    public void setHeader(Map<String, String> headers) {
        mOkHttpClientBuilder = getOkHttpClientBuilderTimeout()
                .addInterceptor(new ParameterInterceptor(headers));
        mRetrofitBuilder.client(mOkHttpClientBuilder.build());
    }

    /**
     * 自定义okHttpClient
     *
     * @param okHttpClient
     */
    public void setHttpClient(OkHttpClient okHttpClient) {
        mRetrofitBuilder.client(okHttpClient);
    }

    /**
     * 设置超时时间，单位秒
     *
     * @param readTimeOut
     * @param connectTimeout
     * @param writeTimeout
     */
    public void setTimeOut(long readTimeOut, long connectTimeout, long writeTimeout) {
        mOkHttpClientBuilder
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS);
        mRetrofitBuilder.client(mOkHttpClientBuilder.build());
    }

    /**
     * 设置无网络时是否缓存
     *
     * @param context
     * @param cache   无网络时true使用缓存的数据
     */
    public void setNoNetworkCache(Context context, boolean cache) {
        if (cache) {
            mOkHttpClientBuilder.addInterceptor(new CacheInterceptor(context));
            mRetrofitBuilder.client(mOkHttpClientBuilder.build());
        }
    }

    public Retrofit getRetrofit() {
        if (mRetrofitBuilder == null) {
            throw new NullPointerException("you need to call init method");
        }
        return mRetrofitBuilder.build();
    }

    private OkHttpClient.Builder getOkHttpClientBuilderTimeout() {
        return new OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS);
    }

}
