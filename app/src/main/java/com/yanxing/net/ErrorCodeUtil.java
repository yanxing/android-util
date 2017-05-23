package com.yanxing.net;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.yanxing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 错误代码
 * Created by 李双祥 on 2017/4/1
 */
public class ErrorCodeUtil {

    public static final int SUCCESS_CODE = 1;

    public static String getException(Throwable e) {
        LogUtil.e("HttpResponse",e.getMessage());
        if (e instanceof JSONException || e instanceof JsonSyntaxException) {
            return "数据解析失败"; //均视为解析错误
        } else if (e instanceof SocketTimeoutException) {
            return "连接服务器超时，请稍后再试";
        } else if (e instanceof ConnectException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            return "网络异常，请检查网络";  //均视为网络错误
        } else {
            return e.getMessage();
        }
    }

    /**
     * 获取错误码提示
     */
    public static String getErrorTip(String content) {
        if (TextUtils.isEmpty(content)) {
            return "未知错误";
        }
        String error = "";
        try {
            error = new JSONObject(content).getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }

    /**
     * 错误代码是否为1,1代表响应成功
     *
     * @param code
     * @return
     */
    public static boolean isErrorSuccess(int code) {
        return code == SUCCESS_CODE;
    }

}
