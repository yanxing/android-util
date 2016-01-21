package com.yanxing.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yanxing.view.LoadingDialog;

/**
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog mLoadingDialog;

    /**
     * 在androidannotations实例化控件后执行，子类需要加上@AfterViews注解
     */
    protected abstract void afterInstanceView();

    /**
     * 显示toast消息
     * @param toast
     */
    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    /**
     * 显示加载框,带文字提示
     */
    public void showLoadingDialog(String tip) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this,tip);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoading() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
