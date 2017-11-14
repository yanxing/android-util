package com.yanxing.networklibrary;


import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 拦截器 ，http错误信息封装
 * Created by 李双祥 on 2017/04/01.
 */
public class ParameterInterceptor implements Interceptor {

    public static final String TAG = "networklibrary";
    private boolean mLog;

    public ParameterInterceptor(boolean log) {
        mLog=log;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        //get参数
        Set<String> parameters = oldRequest.url().queryParameterNames();
        StringBuilder params = new StringBuilder();
        for (String param : parameters) {
            params.append(param).append("=").append(oldRequest.url().queryParameter(param)).append("  ");
        }

        //post参数
        StringBuilder sb = new StringBuilder();
        RequestBody requestBody = oldRequest.body();
        if (requestBody != null) {
            //键值参数
            if (requestBody instanceof FormBody) {
                FormBody formBody = ((FormBody) oldRequest.body());
                if (formBody != null) {
                    for (int i = 0; i < formBody.size(); i++) {
                        sb.append(formBody.encodedName(i)).append("=").append(formBody.encodedValue(i)).append("  ");
                    }
                }
            } else if (requestBody instanceof MultipartBody) {

            } else {//json参数
                final Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                sb.append(buffer.readUtf8());
            }
        }

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(urlBuilder.build())
                .build();
        long b = System.currentTimeMillis();
        Response response = null;
        try {
            response = chain.proceed(newRequest);
        } catch (SocketTimeoutException e) {
        }
        if (response == null) {
            return null;
        }
        //打印日志
        if (mLog){
            long a = System.currentTimeMillis();
            String message = null;
            if (!response.isSuccessful()) {
                message = ErrorCodeUtil.getMessage(response.code());
            }

            String content = response.body().string();
            Log.d(TAG,newRequest.url().url().toString()+"请求耗时："+(a-b)+"ms"+"  请求参数:"+params.toString()+sb.toString()
                    +"请求结果\n"+content);

            ResponseBody body = ResponseBody.create(newRequest.body() == null ? null : newRequest.body().contentType(), content);
            //重新构造body
            Response server = response.newBuilder().body(body).build();
            return server.newBuilder().message(message).build();
        }else {
            return response;
        }
    }
}
