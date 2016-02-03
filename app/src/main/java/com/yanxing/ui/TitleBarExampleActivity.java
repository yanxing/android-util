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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#ff37c14f"));
    }

    @AfterViews
    @Override
    protected void afterInstanceView() {

    }
}
