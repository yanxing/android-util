package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanxing.view.LoadingDialog;

import butterknife.ButterKnife;


/**
 * 基类fragment
 * Created by lishuangxiang on 2015/12/28.
 */
public abstract class BaseFragment extends Fragment{

    public LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view=inflater.inflate(getLayoutResID(),container,false);;
        ButterKnife.bind(this, view);
        afterInstanceView();
        return view;
    }

    /**
     * 子类布局ID
     */
    protected abstract int getLayoutResID();

    /**
     * 实例化控件之后操作
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
