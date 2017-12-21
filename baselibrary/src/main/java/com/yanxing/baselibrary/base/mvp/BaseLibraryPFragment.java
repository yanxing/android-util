package com.yanxing.baselibrary.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanxing.baselibrary.base.BaseLibraryFragment;

/**
 * @author 李双祥 on 2017/11/24.
 */
public abstract class BaseLibraryPFragment<V extends BaseView,T extends BasePresenterImpl<V>> extends BaseLibraryFragment {

    public T mPresenter;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getFragmentManager();
        mPresenter=initPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
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
