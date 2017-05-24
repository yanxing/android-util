package com.yanxing.util;

import android.content.Context;

import com.yanxing.view.LoadingDialog;

import java.lang.ref.WeakReference;

/**
 * 进度对话框
 * Created by 李双祥 on 2017/5/23.
 */
public class LoadDialogUtil {

    private static WeakReference<LoadDialogUtil> WeakReferenceInstance;
    private LoadingDialog mLoadingDialog;

    /**
     * dialog需要activity、fragment的content，这里使用弱引用
     * @return
     */
    public static LoadDialogUtil getInstance() {
        if (WeakReferenceInstance == null || WeakReferenceInstance.get() == null) {
            WeakReferenceInstance = new WeakReference<>(new LoadDialogUtil());
        }
        return WeakReferenceInstance.get();
    }

    private LoadDialogUtil() {
    }


    /**
     * 显示加载框
     */
    protected void showLoadingDialog(Context context) {
        showLoadingDialog(context, null);
    }

    /**
     * 显示加载框,带文字提示
     */
    public void showLoadingDialog(Context context, String msg) {
        WeakReference<Context> reference = new WeakReference<Context>(context);
        if (mLoadingDialog==null){
            mLoadingDialog = new LoadingDialog(reference.get(), msg);
        }
        mLoadingDialog.setCanceledOnTouchOutside(false);
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismiss() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
