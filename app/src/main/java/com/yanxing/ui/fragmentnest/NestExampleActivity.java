package com.yanxing.ui.fragmentnest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;
import com.yanxing.util.LogUtil;

/**
 * fragment嵌套fragment切换例子
 * Created by lishuangxiang on 2016/9/23.
 */

public class NestExampleActivity extends BaseActivity {


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nest_example;
    }

    @Override
    protected void afterInstanceView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NestFragment nestFragment = new NestFragment();
        fragmentTransaction.add(R.id.fragment, nestFragment);
        fragmentTransaction.commit();
    }

}
