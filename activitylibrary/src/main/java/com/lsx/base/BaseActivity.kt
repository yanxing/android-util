package com.lsx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private var layoutId: Int) :AppCompatActivity() {

    protected lateinit var viewBinding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this,layoutId)
        afterInstanceView()
    }


    /**
     * 实例化控件之后的操作
     */
    protected abstract fun afterInstanceView()
}