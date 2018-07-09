package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dianmei.analyzelibrary.AnalyzeAgent;
import com.dianmei.analyzelibrary.Tactic;
import com.yanxing.baselibrary.base.BaseLibraryActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.util.StatusBarUtil;

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends BaseLibraryActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkMode(true, this);
        StatusBarUtil.setStatusBarDarkIcon(getWindow(),true);
        StatusBarUtil.setStatusBarDark6(this);
    }
}
