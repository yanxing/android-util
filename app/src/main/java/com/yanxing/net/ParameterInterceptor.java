package com.yanxing.net;

import com.yanxing.util.LogUtil;

import java.io.IOException;
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

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    public static final String TAG="HttpResponse";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        //get参数
        Set<String> parameters=oldRequest.url().queryParameterNames();
        StringBuilder params=new StringBuilder();
        for (String param:parameters){
            params.append(param).append("=").append(oldRequest.url().queryParameter(param)).append("  ");
        }

        //post参数
        StringBuilder sb=new StringBuilder();
        RequestBody requestBody=oldRequest.body();
        if (requestBody!=null){
            if (requestBody instanceof FormBody){//键值参数
                FormBody formBody=((FormBody) oldRequest.body());
                if (formBody!=null){
                    for (int i=0;i<formBody.size();i++){
                        sb.append(formBody.encodedName(i)).append("=").append(formBody.encodedValue(i)).append("  ");
                    }
                }
            }else if (requestBody instanceof MultipartBody){

            }else {//json参数
                final Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                sb.append(buffer.readUtf8());
            }
        }

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(urlBuilder.build())
                .build();
        long b=System.currentTimeMillis();
        Response response=chain.proceed(newRequest);
        long a=System.currentTimeMillis();
        String message=null;
        if (!response.isSuccessful()) {
            int code = response.code();
            switch (code) {
                case UNAUTHORIZED:
                    message = "网络需要认证";
                    break;
                case FORBIDDEN:
                    message = "网络访问被禁止";
                    break;
                case NOT_FOUND:
                    message = "请求地址错误";
                    break;
                case REQUEST_TIMEOUT:
                    message = "请求超时,请检查当前网络";
                    break;
                case INTERNAL_SERVER_ERROR:
                    message = "内部服务器错误 ";
                    break;
                case BAD_GATEWAY:
                    message = "服务器网关异常";
                    break;
                case SERVICE_UNAVAILABLE:
                    message = "服务器不可用";
                    break;
                case GATEWAY_TIMEOUT:
                    message = "网关超时,请检查当前网络";
                    break;
                default:
                    message = "错误码：" + code + " 请检查网络重试";
                    break;
            }
        }

        String content=response.body().string();
        LogUtil.d(TAG,newRequest.url().url().toString()+"请求耗时："+(a-b)+"s"+"  请求参数:"+params.toString()+sb.toString()
                +"请求结果"+content);

        ResponseBody body= ResponseBody.create(newRequest.body()==null
                ?null:newRequest.body().contentType(),content);
        Response server=response.newBuilder().body(body).build();//重新构造body
        if (message!=null){
            return server.newBuilder().message(message).build();
        }
        return server;
    }
}
