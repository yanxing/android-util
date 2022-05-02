package com.lyxtime.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * MVP模式Activity封装
 * @author 李双祥 on 2022/4/25.
 */
abstract class MVPBaseActivity<T : ViewBinding,V:BaseView,P : BasePresenter<V>>: BaseActivity<T>(){

    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter=setPresenter()
        //子类需要实现V接口
        mPresenter.attachView(this as V)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun setPresenter(): P

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}