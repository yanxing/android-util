package com.yanxing.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yanxing.view.LoadingDialog;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 基类
 * Created by lishuangxiang on 2016/1/21.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    private LoadingDialog mLoadingDialog;
    protected SystemBarTintManager mTintManager;
    //默认启动沉浸式通知栏
    protected boolean mUseStatus=true;
    protected String TAG = getClass().getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        initImmersionStatus(mUseStatus);
        mTintManager = new SystemBarTintManager(this);
        mTintManager .setStatusBarTintEnabled(true);
        mTintManager.setTintColor(0xff37c14f);
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
    public void dismissLoadingDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 使用沉浸式状态栏
     */
    public void initImmersionStatus(boolean use) {
        if (use){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
}
