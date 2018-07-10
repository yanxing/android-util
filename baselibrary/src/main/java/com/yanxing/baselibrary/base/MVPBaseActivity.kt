package com.yanxing.baselibrary.base

import android.os.Bundle


/**
 * MVP基础Activity
 * @author 李双祥 on 2018/7/5.
 */
abstract class MVPBaseActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity() {

    protected var mPresenter: P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建Presenter，不含需要初始的参数
     */
    abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}