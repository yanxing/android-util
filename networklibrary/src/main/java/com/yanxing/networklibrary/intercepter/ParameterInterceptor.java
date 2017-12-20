package com.yanxing.networklibrary.intercepter;


import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.LogUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
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

    private static final String TAG = "networklibrary";
    private Map<String,String> mHeadMap;

    public ParameterInterceptor() {
    }

    public ParameterInterceptor(Map<String,String> head){
        mHeadMap=head;
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

        Request.Builder builder=oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(urlBuilder.build());
        //添加header
        if (mHeadMap!=null){
            for(Map.Entry<String,String> entry:mHeadMap.entrySet()){
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        Request newRequest = builder.build();
        long b = System.currentTimeMillis();
        Response response = null;
        try {
            response = chain.proceed(newRequest);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        long a = System.currentTimeMillis();
        LogUtil.d(TAG,newRequest.url().url().toString()+"请求耗时："+(a-b)+"ms"+"  请求参数:"+params.toString()+sb.toString());
        if (response == null) {
            return null;
        }

        String message = "";
        if (!response.isSuccessful()) {
            message = ErrorCodeUtil.getMessage(response.code());
        }
        String content = response.body().string();
        LogUtil.d(TAG,"请求结果\n"+content);

        ResponseBody body = ResponseBody.create(newRequest.body() == null ? null : newRequest.body().contentType(), content);
        //重新构造body
        Response server = response.newBuilder().body(body).build();
        return server.newBuilder().message(message).build();
    }
}
