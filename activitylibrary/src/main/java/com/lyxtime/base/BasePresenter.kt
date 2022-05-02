package com.lyxtime.base

/**
 * @author 李双祥 on 2022/4/25.
 */
abstract class BasePresenter<V : BaseView> {

    protected var mView: V? = null

    /**
     * 持有view
     */
    fun attachView(view: V) {
        mView = view
    }

    /**
     * 解除持有
     */
    fun detachView() {
        mView = null
    }

}