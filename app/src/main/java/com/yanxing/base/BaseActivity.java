package com.yanxing.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;

/**
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 在androidannotations实例化控件后执行
     */
    protected abstract void afterInstanceView();

    /**
     * 显示toast消息
     * @param toast
     */
    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
    }
}
