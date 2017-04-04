package com.dianmei.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * android6.0以上权限请求
 * Created by 李双祥 on 2017/4/1.
 */
public class PermissionUtil {

    /**
     * 拍照权限
     */
    private static final String CAMERA = "android.permission.CAMERA";
    /**
     * 定位权限
     */
    private static final String LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    /**
     * 定位权限
     */
    private static final String LOCATION1 = "android.permission.ACCESS_COARSE_LOCATION";
    /**
     * 存储写入权限
     */
    private static final String STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    /**
     * 存储读取权限
     */
    private static final String STORAGE1 = "android.permission.READ_EXTERNAL_STORAGE";

    /**
     * 检查权限并申请
     *
     * @param fragment
     * @param permissions
     * @param requestCode
     */
    public static void checkSelfPermission(Fragment fragment, String permissions[], int requestCode) {
        checkSelfPermission(fragment.getActivity(), permissions, requestCode);
    }

    /**
     * 申请权限
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void checkSelfPermission(Activity activity, String permissions[], int requestCode) {
        if (permissions.length > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(permissions, requestCode);
            }
        }
    }

    /**
     * 查找没有被授权的权限
     *
     * @param activity
     * @param permissions
     */
    public static String[] findNeedRequestPermissions(Activity activity, String permissions[]) {
        List<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (activity.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissions[i]);
                }
            }
        }
        return permissionList.toArray(new String[permissionList.size()]);
    }

    /**
     * 权限授权失败提示
     *
     * @param permission
     * @return
     */
    public static String getPermissionTip(String permission) {
        switch (permission) {
            case CAMERA:
                return "拍照授权失败";
            case LOCATION:
                return "定位授权失败";
            case LOCATION1:
                return "定位授权失败";
            case STORAGE:
                return "读取外部存储失败";
            case STORAGE1:
                return "读取外部存储失败";
        }
        return "授权失败";
    }
}
