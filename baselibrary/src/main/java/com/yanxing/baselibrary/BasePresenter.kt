package com.yanxing.baselibrary

/**
 * 基础Presenter，统一销毁持有的View
 * @author 李双祥 on 2018/7/5.
 */
open class BasePresenter<V : BaseView>{

    protected var mIView:V?=null

    /**
     * 绑定view
     */
    fun attachView(view:V){
        mIView=view
    }

    /**
     * 解除绑定
     */
    fun detachView() {
        mIView=null
    }
}