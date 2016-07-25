package com.yanxing.ui;

import com.yanxing.base.BaseActivity;

/**
 * 进度框测试
 * Created by lishuangxiang on 2016/1/21.
 */
public class LoadingDialogExampleActivity extends BaseActivity{

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_loading_dialog_example;
    }

    @Override
    protected void afterInstanceView() {
        showLoadingDialog(getString(R.string.load));
    }
}
