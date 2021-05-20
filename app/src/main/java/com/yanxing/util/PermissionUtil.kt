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
        val launcher = activityFragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback)
        launcher.launch(permissions)
    }

    /**
     * activity申请单组权限
     */
    fun requestPermission(activityFragment: FragmentActivity, permissions: String, activityResultCallback: ActivityResultCallback<Boolean>) {
        val launcher = activityFragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback)
        launcher.launch(permissions)
    }

    /**
     * fragment申请多组权限
     */
    fun requestPermission(fragment: Fragment, permissions: Array<String>, activityResultCallback: ActivityResultCallback<Map<String, Boolean>>) {
        val launcher = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), activityResultCallback)
        launcher.launch(permissions)
    }

    /**
     * fragment申请单组权限
     */
    fun requestPermission(fragment: Fragment, permissions: String, activityResultCallback: ActivityResultCallback<Boolean>) {
        val launcher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission(), activityResultCallback)
        launcher.launch(permissions)
    }

}