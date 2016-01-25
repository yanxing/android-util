package com.yanxing.util;

import android.util.Log;

/**
 * 日志打印,版本发布时关闭日志打印
 * Created by lishuangxiang on 2016/1/25.
 */
public class LogUtil {

    /**
     * 是否打印日志，true打印，false不打印
     * 开发阶段为true,发布时为false
     */
    private static boolean allow = true;

    public static void v(String tag, String msg) {
        if (allow) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (allow) {
            Log.v(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (allow) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (allow) {
            Log.i(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (allow) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (allow) {
            Log.d(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (allow) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (allow) {
            Log.d(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (allow) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (allow) {
            Log.e(tag, msg, tr);
        }
    }
}
