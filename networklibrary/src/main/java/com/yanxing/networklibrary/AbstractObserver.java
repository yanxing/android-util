package com.yanxing.networklibrary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yanxing.networklibrary.dialog.LoadDialog;
import com.yanxing.networklibrary.model.BaseModel;
import com.yanxing.networklibrary.refresh.PullToRefresh;
import com.yanxing.networklibrary.util.ErrorCodeUtil;
import com.yanxing.networklibrary.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 统一处理onCompleted onError方法,onNext处理一部分，如果onNext方法中接口请求返回状态逻辑处理不一样，可重写此方法
 * Created by 李双祥 on 2017/5/23.
 */
public abstract class AbstractObserver<T extends BaseModel> implements Observer<T> {

    protected PullToRefresh mPullToRefresh;
    protected FragmentManager mFragmentManager;
    protected Context mContext;
    /**
     * 是否显示错误信息提示
     */
    protected boolean mIsShowToast = true;

    protected AbstractObserver(Context context) {
        this(context, true);
    }

    protected AbstractObserver(Context context, boolean isShowToast) {
        this.mContext = context;
        this.mIsShowToast = isShowToast;
    }

    /**
     * 含有刷新组件，请求结束完成刷新状态
     *
     * @param pullToRefresh
     */
    protected AbstractObserver(Context context, PullToRefresh pullToRefresh) {
        this(context, pullToRefresh, true);
    }

    /**
     * 含有刷新组件，请求结束完成刷新状态
     *
     * @param pullToRefresh
     */
    protected AbstractObserver(Context context, PullToRefresh pullToRefresh, boolean isShowToast) {
        this.mPullToRefresh = pullToRefresh;
        this.mContext = context;
        this.mIsShowToast = isShowToast;
    }

    /**
     * @param fragmentManager 用来请求结束，移除对话框
     */
    protected AbstractObserver(Context context, FragmentManager fragmentManager) {
        this(context, fragmentManager, true);
    }

    /**
     * @param fragmentManager 用来请求结束，移除对话框
     */
    protected AbstractObserver(Context context, FragmentManager fragmentManager, boolean isShowToast) {
        this.mFragmentManager = fragmentManager;
        this.mContext = context;
        this.mIsShowToast = isShowToast;
    }

    @Override
    public void onComplete() {
        if (mPullToRefresh != null) {
            mPullToRefresh.refreshComplete();
        }
        if (mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                //移除正在显示的对话框
                fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        //打印具体的错误信息
        LogUtil.e(getClass().getName(), Log.getStackTraceString(e));
        if (mContext != null && mIsShowToast) {
            Toast.makeText(mContext, ErrorCodeUtil.getException(e), Toast.LENGTH_LONG).show();
        }
        if (mPullToRefresh != null) {
            mPullToRefresh.refreshComplete();
        }
        if (mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = mFragmentManager.findFragmentByTag(LoadDialog.TAG);
            if (fragment != null) {
                fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (ErrorCodeUtil.isSuccess(t.getStatus())) {
            onCall(t);
        } else {
            if (mContext != null && mIsShowToast) {
                Toast.makeText(mContext, TextUtils.isEmpty(t.getMessage()) ? "" : t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 请求成功返回调用
     *
     * @param t
     */
    public abstract void onCall(T t);

    @Override
    public void onSubscribe(@NonNull Disposable var1) {

    }

}
