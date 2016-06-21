package com.yanxing.ui;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;

import com.yanxing.base.BaseActivity;
import com.yanxing.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 测试finish到Activity，子线程会销毁吗
 * Created by lishuangxiang on 2016/6/15.
 */
@EActivity(R.layout.activity_thread_test)
public class ThreadTestActivity extends BaseActivity {

    @ViewById(R.id.finish)
    Button mButton;

    private int i=0;

    @AfterViews
    @Override
    protected void afterInstanceView() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.d(TAG,"thread finish");
//                if (mButton!=null){
//                    LogUtil.d(TAG,"button is not null");
//                }else {
//                    LogUtil.d(TAG,"button is null");
//                }
//                mButton.setText(getString(R.string.app_name));
//            }
//        },5000);
    }

    @Click(R.id.finish)
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
