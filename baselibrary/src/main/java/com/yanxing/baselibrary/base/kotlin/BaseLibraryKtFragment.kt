package com.yanxing.baselibrary.base.kotlin

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxFragment
import com.yanxing.baselibrary.view.LoadDialog

/**
 *  kotlin基础Fragment
 * @author 李双祥 on 2018/3/2.
 */
abstract class BaseLibraryKtFragment : RxFragment() {

    lateinit var mFragmentManager: FragmentManager;
    val TAG: String = javaClass.name

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(getLayoutResID(), container, false)
        mFragmentManager = fragmentManager
        afterInstanceView()
        return view
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    abstract fun afterInstanceView()

    /**
     * 显示toast消息
     */
    fun showToast(tip: String) {
        if (isAdded && activity != null) {
            val toast = Toast.makeText(activity, tip, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    /**
     * 显示加载框
     */
    fun showLoadingDialog() {
        showLoadingDialog("")
    }

    /**
     * 显示加载框，带文字描述
     */
    fun showLoadingDialog(msg: String) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit()
        } else {
            val loadDialog = LoadDialog()
            if (msg.isBlank()) {
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
        val fragmentTransaction = mFragmentManager.beginTransaction();
        var fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG)
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commitNow();
        }
    }

}