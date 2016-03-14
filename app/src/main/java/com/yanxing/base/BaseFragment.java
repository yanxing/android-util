package com.yanxing.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.yanxing.view.LoadingDialog;


/**
 * 基类fragment
 * Created by lishuangxiang on 2015/12/28.
 */
public abstract class BaseFragment extends Fragment{

    public LoadingDialog loadingDialog;

    /**
     * 在androidannotations实例化控件后执行子类需要加上@AfterViews注解
     */
    protected abstract void afterInstanceView();

    /* 显示toast消息
    * @param toast
    */
    public void showToast(String toast){
        if (getActivity()!=null){
            Toast.makeText(getActivity(),toast,Toast.LENGTH_LONG).show();
        }
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
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity(), msg);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoadingDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
