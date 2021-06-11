package com.lsx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> :AppCompatActivity() {

    protected lateinit var viewBinding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this,getLayoutResID())
        afterInstanceView()
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract fun getLayoutResID(): Int

    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()
}