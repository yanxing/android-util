package com.yanxing.util;

import android.util.Log;

/**
 * 日志打印
 * Created by lishuangxiang on 2017/12/20.
 */
public class LogUtil {

    private static boolean mAllow = true;

    public static void v(String tag, String msg) {
        if (mAllow) {
            print(1,tag,msg);
        }
    }

    public static void d(String tag, String msg) {
        if (mAllow) {
            print(2,tag,msg);
        }
    }

    public static void i(String tag, String msg) {
        if (mAllow) {
            print(3,tag,msg);
        }
    }

    public static void w(String tag, String msg) {
        if (mAllow) {
            print(4,tag,msg);
        }
    }

    public static void e(String tag, String msg) {
        if (mAllow) {
            print(5,tag,msg);
        }
    }

    private static void print(int key, String tag, String msg) {
        int maxLogSize = 2001;
        for (int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = Math.min(end, msg.length());
            switch (key) {
                case 1:
                    Log.v(tag, msg.substring(start, end));
                    break;
                case 2:
                    Log.d(tag, msg.substring(start, end));
                    break;
                case 3:
                    Log.i(tag, msg.substring(start, end));
                    break;
                case 4:
                    Log.w(tag, msg.substring(start, end));
                    break;
                case 5:
                    Log.e(tag, msg.substring(start, end));
                    break;
                    default:
            }
        }
    }
}
