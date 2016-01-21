package com.yanxing.ui;

import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * 进度框测试
 * Created by lishuangxiang on 2016/1/21.
 */
@EActivity(R.layout.activity_loading_dialog_example)
public class LoadingDialogExampleActivity extends BaseActivity{

    @AfterViews
    @Override
    protected void afterInstanceView() {
        showLoadingDialog("加载中...");
    }
}
