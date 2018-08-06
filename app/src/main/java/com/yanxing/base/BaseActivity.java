package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.yanxing.baseextendlibrary.view.LoadDialog;
import com.yanxing.util.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends com.yanxing.baselibrary.BaseActivity {

    public String TAG=getClass().getName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarDarkMode(true, this);
        StatusBarUtil.setStatusBarDarkIcon(getWindow(),true);
        StatusBarUtil.setStatusBarDark6(this);
    }

    /**
     * 显示加载框
     */
    protected void showLoadingDialog() {
        showLoadingDialog(null);
    }

    /**
     * 显示加载框,带文字提示
     */
    public void showLoadingDialog(String msg) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit();
        } else {
            LoadDialog loadDialog = new LoadDialog();
            if (msg != null) {
                Bundle bundle = new Bundle();
                bundle.putString("tip", msg);
                loadDialog.setArguments(bundle);
            }
            loadDialog.show(fragmentTransaction, LoadDialog.TAG);
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoadingDialog() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            //移除正在显示的对话框
            fragmentTransaction.remove(fragment).commitNow();
        }
    }
}
