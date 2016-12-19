package com.yanxing.ui;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.CommonUtil;

/**
 * titlebarlibrary使用
 * Created by lishuangxiang on 2016/2/3.
 */
public class TitleBarExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_titlebar_example;
    }

    @Override
    protected void afterInstanceView() {
        CommonUtil.setStatusBarDarkMode(false,this);

    }
}
