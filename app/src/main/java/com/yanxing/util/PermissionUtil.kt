package com.yanxing.util

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * ActivityResultContract申请权限方式
 */
object PermissionUtil {


    /**
     * activity申请权限多组权限
     */
    fun requestPermission(activityFragment: FragmentActivity, permissions: Array<String>, activityResultCallback: ActivityResultCallback<Map<String, Boolean>>) {
        activityFragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback).launch(permissions)
    }

    /**
     * activity申请单组权限
     */
    fun requestPermission(activityFragment: FragmentActivity, permission: String, activityResultCallback: ActivityResultCallback<Boolean>) {
        activityFragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback).launch(permission)
    }

    /**
     * fragment申请多组权限
     */
    fun requestPermission(fragment: Fragment, permissions: Array<String>, activityResultCallback: ActivityResultCallback<Map<String, Boolean>>) {
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback).launch(permissions)
    }

    /**
     * fragment申请单组权限
     */
    fun requestPermission(fragment: Fragment, permission: String, activityResultCallback: ActivityResultCallback<Boolean>) {
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback).launch(permission)
    }

}