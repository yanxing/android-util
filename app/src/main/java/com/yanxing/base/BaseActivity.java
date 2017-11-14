package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.yanxing.util.CommonUtil;
import com.yanxing.view.LoadDialog;

import butterknife.ButterKnife;

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends RxFragmentActivity {

    protected String TAG = getClass().getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        CommonUtil.setStatusBarDarkMode(true,this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        afterInstanceView();
    }

    /**
     * 子类布局ID
     */
    protected abstract int getLayoutResID();

    /**
     * 实例化控件之后操作
     */
    protected abstract void afterInstanceView();

    /**
     * 显示toast消息
     */
    public void showToast(String toast){
        Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_LONG).show();
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit();
        } else {
            LoadDialog loadDialog = new LoadDialog();
            if (tip != null) {
                Bundle bundle=new Bundle();
                bundle.putString("tip",tip);
                loadDialog.setArguments(bundle);
            }
            loadDialog.show(fragmentTransaction, LoadDialog.TAG);
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoadingDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commitNow();
        }
    }
}
