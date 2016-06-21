package com.yanxing.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yanxing.view.LoadingDialog;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
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
        initImmersionStatus(mUseStatus);
        mTintManager = new SystemBarTintManager(this);
        mTintManager .setStatusBarTintEnabled(true);
        mTintManager.setTintColor(0xff37c14f);
    }

    /**
     * 在androidannotations实例化控件后执行(onCreate之后，onStart之前)，子类需要加上@AfterViews注解
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
