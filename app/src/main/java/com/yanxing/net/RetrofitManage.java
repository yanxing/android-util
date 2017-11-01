package com.yanxing.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lishuangxiang on 2017/04/01.
 */
public class RetrofitManage {

    private Retrofit mRetrofit;

    private RetrofitManage() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new ParameterInterceptor())
                .build();

        mRetrofit=new Retrofit.Builder()
                .baseUrl(ConstantAPI.SERVICE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitManage getInstance() {
        return SingletonHolder.retrofitManage;
    }

    private static class SingletonHolder {
        private static final RetrofitManage retrofitManage = new RetrofitManage();
    }

    public Retrofit initRetrofit(Retrofit retrofit) {
        return (mRetrofit=retrofit);
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
