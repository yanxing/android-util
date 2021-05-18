package com.yanxing.baselibrary

import android.os.Bundle

/**
 * MVP基础Fragment
 * @author 李双祥 on 2018/7/9.
 */
abstract class MVPBaseFragment<V : BaseView, P : BasePresenter<V>> : BaseFragment() {

    protected var mPresenter: P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建无参Presenter
     */
    protected abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}