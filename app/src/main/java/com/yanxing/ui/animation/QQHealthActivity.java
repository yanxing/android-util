package com.yanxing.ui.animation;

import com.photo.util.AppUtil;
import com.yanxing.base.BaseActivity;
import com.yanxing.ui.R;

/**
 * 防QQ健康，来自
 * https://github.com/burgessjp/MaterialDesignDemo/blob/master/app/src/main/java/ren/solid/materialdesigndemo/view/QQHealthView.java
 * Created by lishuangxiang on 2016/8/2.
 */
public class QQHealthActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_qq_health;
    }

    @Override
    protected void afterInstanceView() {
        AppUtil.setStatusBarDarkMode(true,this);
    }
}
