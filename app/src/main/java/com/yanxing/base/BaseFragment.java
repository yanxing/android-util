package com.yanxing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.yanxing.view.LoadDialog;

import butterknife.ButterKnife;


/**
 * 基类fragment
 * Created by lishuangxiang on 2015/12/28.
 */
public abstract class BaseFragment extends RxFragment{

    protected String TAG = getClass().getName();

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
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit();
        } else {
            LoadDialog loadDialog = new LoadDialog();
            if (msg != null) {
                Bundle bundle=new Bundle();
                bundle.putString("tip",msg);
                loadDialog.setArguments(bundle);
            }
            loadDialog.show(fragmentTransaction, LoadDialog.TAG);
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoadingDialog() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(LoadDialog.TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commitNow();
        }
    }
}
