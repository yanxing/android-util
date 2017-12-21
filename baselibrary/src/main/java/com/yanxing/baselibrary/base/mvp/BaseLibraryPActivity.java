package com.yanxing.baselibrary.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanxing.baselibrary.base.BaseLibraryActivity;

/**
 * @author 李双祥 on 2017/11/20.
 */
public abstract class BaseLibraryPActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends BaseLibraryActivity {

    public T mPresenter;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        mPresenter=initPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.onDestroy();
        }
    }

    /**
     * 实例化个Presenter
     * @return
     */
    protected abstract T initPresenter();
}
