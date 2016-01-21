package com.yanxing.base;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;

/**
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 在androidannotations实例化控件后执行
     */
    protected abstract void afterInstanceView();
}
