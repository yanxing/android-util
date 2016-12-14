package com.yanxing.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * android6.0以上权限请求
 * Created by lishuangxiang on 2016/12/12.
 */
public class PermissionUtil {

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
     * 检查权限并申请
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static void checkSelfPermission(Activity activity, String permissions[], int requestCode) {
        List<String> permissionList = findNeedRequestPermissions(activity, permissions);
        if (permissionList.size()>0){
            activity.requestPermissions(permissionList.toArray(new String[permissionList.size()]), requestCode);
        }
    }

    /**
     * 查找没有被授权的权限
     *
     * @param activity
     * @param permissions
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private static List<String> findNeedRequestPermissions(Activity activity, String permissions[]) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (activity.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        return permissionList;
    }
}
