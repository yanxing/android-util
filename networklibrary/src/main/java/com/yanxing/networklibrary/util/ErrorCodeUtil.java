package com.yanxing.networklibrary.util;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.yanxing.networklibrary.intercepter.ParameterInterceptor;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 错误代码
 * Created by 李双祥 on 2017/4/1
 */
public class ErrorCodeUtil {

    private static final int SUCCESS_CODE = 1;
    private static final String TAG = "networklibrary";
    private static final String PARSE_JSON_FAIL = "数据解析失败";
    private static final String NETWORK_ERROR = "网络异常，请检查网络";
    private static final String CONNET_SERVICE_TIME_OUT = "连接服务器超时，请稍后再试";

    private static final int NOT_FOUND = 404;
    private static final int METHOD_ERROR = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final String ADDRESS_ERROR = "请求地址错误";
    private static final String TIME_OUT = "请求超时,请检查当前网络";
    private static final String SERVICE_INTERNAL_ERROR = "服务器内部错误";
    private static final String SERVICE_NOT_AVAILABLE = "服务器不可用";
    private static final String REQUEST_METHOD_ERROR = "请求方法出错";

    public static String getException(Throwable e) {
        Log.e(TAG, e.getMessage());
        if (e instanceof JSONException || e instanceof JsonSyntaxException) {
            return PARSE_JSON_FAIL;
        } else if (e instanceof ConnectException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            return NETWORK_ERROR;
        } else if (e instanceof SocketTimeoutException || e.getMessage().contains(ParameterInterceptor.class.getName())) {
            return CONNET_SERVICE_TIME_OUT;
        } else {
            return e.getMessage();
        }
    }

    /**
     * 获取错误代码代表的错误信息
     *
     * @param code
     * @return
     */
    public static String getMessage(int code) {
        String message;
        switch (code) {
            case NOT_FOUND:
                message = ADDRESS_ERROR;
                break;
            case REQUEST_TIMEOUT:
                message = TIME_OUT;
                break;
            case INTERNAL_SERVER_ERROR:
                message = SERVICE_INTERNAL_ERROR;
                break;
            case SERVICE_UNAVAILABLE:
                message = SERVICE_NOT_AVAILABLE;
                break;
            case METHOD_ERROR:
                message = REQUEST_METHOD_ERROR;
                break;
            default:
                message = "错误码：" + code + "请检查网络重试";
                break;
        }
        return message;
    }

    /**
     * 错误代码是否为1,1代表响应成功
     *
     * @param code
     * @return
     */
    public static boolean isSuccess(int code) {
        return code == SUCCESS_CODE;
    }

}
