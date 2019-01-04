package com.yanxing.base


import android.os.Bundle

import com.yanxing.commonlibrary.view.LoadDialog


/**
 * 基类fragment
 * Created by lishuangxiang on 2015/12/28.
 */
abstract class BaseFragment : com.yanxing.baselibrary.BaseFragment() {

    /**
     * 显示加载框
     */
    protected fun showLoadingDialog() {
        showLoadingDialog(null)
    }

    /**
     * 显示加载框,带文字提示
     */
    fun showLoadingDialog(msg: String?) {
        val fragmentTransaction = mFragmentManager?.beginTransaction()
        val fragment = mFragmentManager?.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            fragmentTransaction?.remove(fragment)?.commit()
        } else {
            val loadDialog = LoadDialog()
            if (msg != null) {
                val bundle = Bundle()
                bundle.putString("tip", msg)
                loadDialog.arguments = bundle
            }
            if (fragmentTransaction!=null) {
                loadDialog.show(fragmentTransaction, LoadDialog.TAG)
            }
        }
    }

    /**
     * 隐藏加载框
     */
    fun dismissLoadingDialog() {
        val fragmentTransaction = mFragmentManager?.beginTransaction()
        val fragment = mFragmentManager?.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            //移除正在显示的对话框
            fragmentTransaction?.remove(fragment)?.commitNow()
        }
    }


}
