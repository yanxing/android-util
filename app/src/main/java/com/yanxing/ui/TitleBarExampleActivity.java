package com.yanxing.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yanxing.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * titlebarlibrary使用
 * Created by lishuangxiang on 2016/2/3.
 */
@EActivity(R.layout.activity_titlebar_example)
public class TitleBarExampleActivity extends BaseActivity {

    @AfterViews
    @Override
    protected void afterInstanceView() {

    }
}
