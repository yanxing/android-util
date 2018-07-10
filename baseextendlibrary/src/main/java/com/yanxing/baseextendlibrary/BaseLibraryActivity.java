package com.yanxing.baseextendlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yanxing.baseextendlibrary.view.LoadDialog;

import butterknife.ButterKnife;

/**
 * Created by 李双祥 on 2017/10/19.
 */
public abstract class BaseLibraryActivity extends RxAppCompatActivity {

    protected FragmentManager mFragmentManager;
    protected String TAG = getClass().getName();

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        afterInstanceView();
    }

    /**
     * 显示toast消息
     *
     * @param tip
     */
    public void showToast(String tip) {
        Toast toast = Toast.makeText(getApplicationContext(), tip, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 子类布局，例如R.layout.activity_main
     */
    protected abstract int getLayoutResID();

    /**
     * 实例化控件之后操作
     */
    protected abstract void afterInstanceView();

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
