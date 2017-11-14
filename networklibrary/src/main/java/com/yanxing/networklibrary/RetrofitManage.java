package com.yanxing.networklibrary;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lishuangxiang on 2017/04/01.
 */
public class RetrofitManage {

    private static Retrofit mRetrofit;
    private static String url;
    private static boolean mLog;

    private RetrofitManage() {
        if (mRetrofit != null) {
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new ParameterInterceptor(mLog))
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Retrofit baseUrl
     *
     * @param baseUrl
     * @param log true打印请求参数和返回数据
     */
    public static void init(String baseUrl,boolean log) {
        mLog=log;
        url = baseUrl;
    }

    /**
     * @param retrofit
     */
    public static void init(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public static RetrofitManage getInstance() {
        return SingletonHolder.retrofitManage;

    }

    private static class SingletonHolder {
        private static final RetrofitManage retrofitManage = new RetrofitManage();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}
