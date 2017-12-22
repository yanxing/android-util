package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dianmei.analyzelibrary.AnalyzeAgent;
import com.dianmei.analyzelibrary.Tactic;
import com.yanxing.baselibrary.base.BaseLibraryActivity;
import com.yanxing.util.CommonUtil;

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends BaseLibraryActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtil.setStatusBarDarkMode(true, this);
        CommonUtil.setStatusBarDarkIcon(getWindow(),true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyzeAgent.AnalyzeConfig analyzeConfig=new AnalyzeAgent.AnalyzeConfig(Tactic.HOUR.name(),"guanwang","yanxing",true);
        AnalyzeAgent.init(analyzeConfig);
        AnalyzeAgent.onResume(this,TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyzeAgent.onPause(TAG);
    }
}
