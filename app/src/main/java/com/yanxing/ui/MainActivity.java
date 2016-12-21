package com.yanxing.ui;

import com.yanxing.base.BaseActivity;

public class MainActivity extends BaseActivity{

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterInstanceView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, new MainFragment(), MainFragment.class.getName())
                .commit();
    }
}
