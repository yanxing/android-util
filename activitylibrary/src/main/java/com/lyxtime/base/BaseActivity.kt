package com.lyxtime.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * 基类
 * @author 李双祥 on 2022/4/18.
 */
abstract  class BaseActivity<T : ViewBinding> :AppCompatActivity(){

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=initBinding()
        setContentView(binding.root)
        afterInstanceView()
    }

    /**
     * 子类绑定ViewBinding，kotlin-android-extensions已废弃
     */
    abstract fun initBinding(): T

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()

    /**
     * 显示fragment，隐藏其他的
     */
    fun switchFragment(targetFragment: Fragment, layoutId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val data = supportFragmentManager.fragments
        if (data.size > 0) {
            for (fragment in data) {
                fragment?.let {
                    transaction.hide(fragment)
                }
            }
        }
        if (!targetFragment.isAdded) {
            transaction
                .add(layoutId, targetFragment)
                .commitAllowingStateLoss()
        } else {
            transaction
                .show(targetFragment)
                .commitAllowingStateLoss()
        }
    }

    protected fun add(fragment: Fragment, id: Int) {
        val tag = fragment.javaClass.toString()
        supportFragmentManager
            .beginTransaction()
            .add(id, fragment, tag)
            .commitAllowingStateLoss()
    }
}