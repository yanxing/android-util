package com.yanxing.baselibrary.base.mvp;

/**
 * 默认实现BasePresenter接口
 * @author 李双祥 on 2017/11/20.
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter {

    protected T mBaseView;

    @Override
    public void onDestroy() {
        mBaseView=null;
    }
}
