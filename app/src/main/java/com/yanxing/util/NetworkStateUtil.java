package com.yanxing.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 检测手机网络是否可用和当前网络类型
 * Created by lishuangxiang on 2016/1/25.
 */
public class NetworkStateUtil {

    public static final String NO_NETWORK_CONNECTED="当前网络不可用，请检查网络连接再试";

    /**
     * 网络是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * WIFI是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isWifiConnected(Context context) {
        if (getConnectedType(context)==ConnectivityManager.TYPE_WIFI){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 移动网络是否可用
     *
     * @param context
     * @return true 可用，false不可用
     */
    public static boolean isMobileConnected(Context context) {
        if (getConnectedType(context)==ConnectivityManager.TYPE_MOBILE){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取网络连接类型
     *
     * @param context
     * @return -1 获取失败
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }
}
