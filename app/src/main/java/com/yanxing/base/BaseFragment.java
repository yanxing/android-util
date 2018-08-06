package com.yanxing.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.yanxing.baseextendlibrary.view.LoadDialog;


/**
 * 基类fragment
 * Created by lishuangxiang on 2015/12/28.
 */
public abstract class BaseFragment extends com.yanxing.baselibrary.BaseFragment {

    public String TAG=getClass().getName();

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
