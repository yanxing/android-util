package com.yanxing.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 获取状态栏高度，如果是刘海屏则获取刘海高度，Android O版本，强制启动刘海区绘制模式，刘海区不绘制模式
 *
 * @author 李双祥 on 2018/9/26.
 */
public class NotchPhoneUtil {

    /**
     * 设置是否启动刘海区绘制模式
     * @param enable
     * @param activity
     */
    public static void setNotchEnable(boolean enable,Activity activity){
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            if (enable) {
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }else {
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            }
            activity.getWindow().setAttributes(lp);
        }
    }

    /**
     * 获取状态栏高度，如果是刘海屏则获取刘海高度，目前仅适配华为、oppo和小米刘海屏
     *
     * @param activity
     * @return
     */
    public static int getNotchHigh(Activity activity) {
        int statusHigh = CommonUtil.getStatusHeight(activity);
        //如果是小米手机
        if (PhoneBrandUtil.isXiaomi()) {
            //是刘海屏
            if ("1".equals(getProperty("ro.miui.notch", "0"))) {
                //https://dev.mi.com/console/doc/detail?pId=1293，取刘海高度89
                return 89;
            } else {
                return statusHigh;
            }
        } else if (PhoneBrandUtil.isHuawei()) {
            if (hasNotchInScreen(activity)) {
                return getNotchSize(activity)[1];
            } else {
                return statusHigh;
            }
        } else if (PhoneBrandUtil.isOppo()) {
            if (hasNotInOppoScreen(activity)) {
                //https://open.oppomobile.com/wiki/doc#id=10159
                return 80;
            } else {
                return statusHigh;
            }
        } else {
            return statusHigh;
        }
    }

    /**
     * 是否是刘海屏，仅支持小米华为oppo
     *
     * @param activity
     * @return
     */
    public static boolean isNotchPhone(Activity activity) {
        if (PhoneBrandUtil.isXiaomi()) {
            return "1".equals(getProperty("ro.miui.notch", "0"));
        } else if (PhoneBrandUtil.isHuawei()) {
            return hasNotchInScreen(activity);
        } else if (PhoneBrandUtil.isOppo()) {
            return hasNotInOppoScreen(activity);
        } else {
            return false;
        }
    }

    /**
     * 获取系统属性
     *
     * @param key
     * @param defaultValue
     * @return
     */
    private static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, "0"));
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    /**
     * 华为手机是否有刘海屏
     *
     * @param context
     * @return
     */
    private static boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为手机刘海宽高度
     *
     * @param context
     * @return int[0]刘海宽度，int[1]刘海高度
     */
    private static int[] getNotchSize(Context context) {
        int[] ret = new int[]{1080, CommonUtil.dp2px(context, 85)};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }

    /**
     * oppo手机是否是刘海屏
     *
     * @param context
     * @return
     */
    private static boolean hasNotInOppoScreen(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        } catch (Exception e) {
            return false;
        }
    }

}
