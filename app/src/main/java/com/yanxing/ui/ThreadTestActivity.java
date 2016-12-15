package com.yanxing.ui;

import android.os.Handler;
import android.widget.Button;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试finish到Activity，子线程会销毁吗
 * Created by lishuangxiang on 2016/6/15.
 */
public class ThreadTestActivity extends BaseActivity {

    @BindView(R.id.finish)
    Button mButton;

    private int i=0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_thread_test;
    }

    @Override
    protected void afterInstanceView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG,"thread finish");
                if (mButton!=null){
                    LogUtil.d(TAG,"button is not null");
                }else {
                    LogUtil.d(TAG,"button is null");
                }
                mButton.setText(getString(R.string.app_name));
            }
        },5000);
    }

    @OnClick(R.id.finish)
    public void onClick(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"onDestroy");
        if (mButton!=null){
            LogUtil.d(TAG,"onDestroy button is not null");
        }else {
            LogUtil.d(TAG,"onDestroy button is null");
        }
    }
}
