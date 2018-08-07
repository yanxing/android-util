package com.yanxing.base

import android.os.Bundle

import com.yanxing.baseextendlibrary.view.LoadDialog
import com.yanxing.util.StatusBarUtil

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
abstract class BaseActivity : com.yanxing.baselibrary.BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarDarkMode(true, this)
        StatusBarUtil.setStatusBarDarkIcon(window, true)
        StatusBarUtil.setStatusBarDark6(this)
    }

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
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit()
        } else {
            val loadDialog = LoadDialog()
            if (msg != null) {
                val bundle = Bundle()
                bundle.putString("tip", msg)
                loadDialog.arguments = bundle
            }
            loadDialog.show(fragmentTransaction, LoadDialog.TAG)
        }
    }

    /**
     * 隐藏加载框
     */
    fun dismissLoadingDialog() {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            //移除正在显示的对话框
            fragmentTransaction.remove(fragment).commitNow()
        }
    }
}
