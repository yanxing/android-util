package com.yanxing.networklibrary.intercepter;


import android.text.TextUtils;
import android.util.Log;

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
    private Map<String, String> mHeadMap;

    public ParameterInterceptor() {
    }

    public ParameterInterceptor(Map<String, String> head) {
        mHeadMap = head;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        Request.Builder builder = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(urlBuilder.build());
        //添加header
        if (mHeadMap != null) {
            for (Map.Entry<String, String> entry : mHeadMap.entrySet()) {
                if (!TextUtils.isEmpty(entry.getValue())) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
        //不打印，不拼接参数，不重新构造
        if (!LogUtil.isDebug){
            return chain.proceed(builder.build());
        }


        //get请求的参数
        Set<String> parameters = oldRequest.url().queryParameterNames();
        StringBuilder getParams = new StringBuilder();
        for (String param : parameters) {
            getParams
                    .append(param)
                    .append("=")
                    .append(oldRequest.url().queryParameter(param))
                    .append("  ");
        }


        //post请求的参数
        StringBuilder postParams = new StringBuilder();
        RequestBody requestBody = oldRequest.body();
        if (requestBody != null) {
            //键值参数
            if (requestBody instanceof FormBody) {
                FormBody formBody = ((FormBody) oldRequest.body());
                if (formBody != null) {
                    for (int i = 0; i < formBody.size(); i++) {
                        postParams
                                .append(formBody.encodedName(i))
                                .append("=")
                                .append(formBody.encodedValue(i))
                                .append("  ");
                    }
                }
            } else if (requestBody instanceof MultipartBody) {

            } else {//json参数
                final Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                postParams.append(buffer.readUtf8());
            }
        }

        //header参数
        StringBuilder headerParams = new StringBuilder();
        Request newRequest = builder.build();
        for (String name : newRequest.headers().names()) {
            headerParams
                    .append(name)
                    .append("=")
                    .append(newRequest.headers().get(name))
                    .append("  ");
        }

        long b = System.currentTimeMillis();
        //此句异常，将不执行后续打印耗时代码
        Response response = chain.proceed(newRequest);
        long a = System.currentTimeMillis();
        String message = "";
        if (!response.isSuccessful()) {
            message = ErrorCodeUtil.getMessage(response.code());
        }
        String content = response.body().string();
        String headParamsStr = TextUtils.isEmpty(headerParams.toString()) ? "" : "  header参数" + headerParams.toString();
        LogUtil.d(TAG, newRequest.url().url().toString() + "  请求参数:" + getParams.toString() + postParams.toString() + headParamsStr
                +"\n请求耗时：" + (a - b) + "ms，" + "请求结果\n" + content + "\n");

        ResponseBody body = ResponseBody.create(newRequest.body() == null ? null : newRequest.body().contentType(), content);
        //重新构造body
        Response server = response.newBuilder().body(body).build();
        return server.newBuilder().message(message).build();
    }
}
