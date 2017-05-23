package com.yanxing.util;

import android.content.Context;

import com.yanxing.view.LoadingDialog;

import java.lang.ref.WeakReference;

/**
 * 进度对话框
 * Created by 李双祥 on 2017/5/23.
 */
public class LoadDialogUtil {

    private static LoadingDialog mLoadingDialog;

    /**
     * 显示加载框
     */
    protected void showLoadingDialog(Context context) {
        showLoadingDialog(context,null);
    }

    /**
     * 显示加载框,带文字提示
     */
    public static void showLoadingDialog(Context context,String msg) {
        WeakReference<Context> reference=new WeakReference<Context>(context);
        mLoadingDialog=new LoadingDialog(reference.get(),msg);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    public static void dismiss() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
