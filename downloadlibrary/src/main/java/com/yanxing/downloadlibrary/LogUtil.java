package com.yanxing.downloadlibrary;

import android.util.Log;

/**
 * 日志打印
 * Created by lishuangxiang on 2016/1/25.
 */
public class LogUtil {

    /**
     * 是否打印日志，true打印，false不打印
     */
    private static boolean mAllow = false;

    public static void setmAllow(boolean mAllow) {
        LogUtil.mAllow = mAllow;
    }

    public static void v(String tag, String msg) {
        if (mAllow) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (mAllow) {
            Log.v(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (mAllow) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (mAllow) {
            Log.i(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (mAllow) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (mAllow) {
            Log.d(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (mAllow) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (mAllow) {
            Log.d(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (mAllow) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (mAllow) {
            Log.e(tag, msg, tr);
        }
    }
}
