package com.yanxing.baselibrary.base.kotlin

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.yanxing.baselibrary.view.LoadDialog

/**
 * kotlin基础Activity
 * @author 李双祥 on 2018/3/2.
 */
abstract class BaseLibraryKtActivity : RxAppCompatActivity() {

    lateinit var mFragmentManager: FragmentManager;
    val TAG:String=javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentManager = supportFragmentManager
        setContentView(getLayoutResID())
        afterInstanceView()
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
        val toast = Toast.makeText(applicationContext, tip, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
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