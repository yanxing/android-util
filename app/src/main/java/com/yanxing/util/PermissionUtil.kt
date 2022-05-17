package com.yanxing.util

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * ActivityResultContract申请权限方式
 * @author 李双祥 on 2022/5/17.
 */
object PermissionUtil {


    /**
     * activity申请权限多组权限，在需要触发权限申请的地方还需要调用launch方法，不能直接写在触发事件方法里面然后直接调用launch方法
     */
    fun requestMultiplePermission(activityFragment: FragmentActivity, activityResultCallback: ActivityResultCallback<Map<String, Boolean>>)
            :ActivityResultLauncher<Array<String>> {
        return activityFragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback)
    }

    /**
     * activity申请单组权限,在需要触发权限申请的地方还需要调用launch方法
     */
    fun requestPermission(activityFragment: FragmentActivity, activityResultCallback: ActivityResultCallback<Boolean>):ActivityResultLauncher<String> {
        return activityFragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback)
    }

    /**
     * fragment申请多组权限,在需要触发权限申请的地方还需要调用launch方法
     */
    fun requestMultiplePermission(fragment: Fragment, activityResultCallback: ActivityResultCallback<Map<String, Boolean>>) :ActivityResultLauncher<Array<String>> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback)
    }

    /**
     * fragment申请单组权限,在需要触发权限申请的地方还需要调用launch方法
     */
    fun requestPermission(fragment: Fragment,activityResultCallback: ActivityResultCallback<Boolean>):ActivityResultLauncher<String> {
        return fragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback)
    }

}