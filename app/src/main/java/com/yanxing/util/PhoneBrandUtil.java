package com.yanxing.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * @author 李双祥 on 2018/8/1.
 */
public class PhoneBrandUtil {

    /**
     * 是否是魅族手机（系统）
     * @return
     */
    public static boolean isFlyme() {
        return "Meizu".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 是否是华为荣耀
     * @return
     */
    public static boolean isHuawei(){
        return "huawei".equalsIgnoreCase(Build.BRAND)||"Honor".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 是否是三星
     * @return
     */
    public static boolean isSamsung(){
        return "samsung".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 是否是小米
     * @return
     */
    public static boolean isXiaomi(){
        return "xiaomi".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 是否是oppo
     * @return
     */
    public static boolean isOppo(){
        return "oppo".equalsIgnoreCase(Build.BRAND);
    }

    /**
     * 三星手机跳转应用市场
     * @param context
     * @param packageName
     */
    public static void goToSamsungappsMarket(Context context, String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
