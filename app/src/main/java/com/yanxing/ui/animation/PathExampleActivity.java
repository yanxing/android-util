package com.yanxing.ui.animation;

import com.photo.util.AppUtil;
import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;

/**
 * Path类
 * Created by lishuangxiang on 2016/8/2.
 */
public class PathExampleActivity extends BaseActivity {


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_path_example;
    }

    @Override
    protected void afterInstanceView() {
        AppUtil.setStatusBarDarkMode(true,this);

    }
}
